package ac.uk.dundee.agile7;

import ac.uk.dundee.agile7.entity.PriceEntry;
import ac.uk.dundee.agile7.entity.Procedure;
import ac.uk.dundee.agile7.entity.Provider;
import ac.uk.dundee.agile7.service.SearchService;
import com.opencsv.CSVReader;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Class to load data from the CSV into the database
 */
@Component
public class DataLoader {


    private HashMap<Integer, LatLng> latLngMap; //for reading in Provider coordinates efficiently
    private SearchService mdb;

    public DataLoader(SearchService mdb) {
        this.mdb = mdb;

        this.latLngMap = new HashMap<>();
        try {
            String PRICES_CSV = "prices.csv";
            String LOCATIONS_CSV = "locations.csv";
            readFromCsvInResources(PRICES_CSV, LOCATIONS_CSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAllLocations for the file at locationFilePath.
     * @param locationFilePath          path to the locations csv.
     * @throws FileNotFoundException    if the file does not exist
     */
    protected void readAllLocations(String locationFilePath) throws FileNotFoundException {

        File file = loadFile(locationFilePath);
        readAllLocations(file);
    }

    /**
     * Creates a File object from its filepath in resources
     * @param filePath  path to the file in resources
     * @return  a File object opened at the filepath
     */
    private File loadFile(String filePath) {
        ClassPathResource classPathResource = new ClassPathResource(filePath);

        File file = null;
        InputStream inputStream = null;
        // see https://stackoverflow.com/questions/25869428/classpath-resource-not-found-when-running-as-jar
        try {
            inputStream = classPathResource.getInputStream();
            File somethingFile = File.createTempFile("test", ".csv");
            FileUtils.copyInputStreamToFile(inputStream, somethingFile);

            file = somethingFile;


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            IOUtils.closeQuietly(inputStream);
        }
        return file;
    }

    //for checking outcome in tests
    protected LatLng getLatLngAt(Integer providerID) {
        return latLngMap.get(providerID);
    }

    /**
     * Reads in a csv file with location data to later use it when reading in prices. Will be stored in the latLngMap field.
     * File format: providerID, latitude, longitude
     * @param locationFile path to the location file in resources
     * @throws FileNotFoundException if the file is not found
     */
    protected void readAllLocations(File locationFile) throws FileNotFoundException {
        CSVReader csvReader = new CSVReader(new FileReader(locationFile));
        Iterator<String[]> it = csvReader.iterator();
        it.next(); //skip the header line
        while (it.hasNext()) {
            String[] nextLine = it.next();
            // nextLine[] is an array of values from the line
            //format: providerID, latitude, longitude
            Integer providerID = Integer.parseInt(nextLine[0]);
            double lat = Double.parseDouble(nextLine[1]);
            double lng = Double.parseDouble(nextLine[2]);
            latLngMap.put(providerID, new LatLng(lat, lng));
        }
    }


    /**
     * Reads the price file data into the database. The readAllLocations function must be called before this one to read in the coordinates
     * File format: CodeAndNAme, Provider Id, Provider Name, Street Address, City, State, Zip Code,HRR Description,Total Discharges,Average Covered Charges,Average Total Payments,Average Medicare Payments
     * @param priceFile path to the price data file in resources
     * @throws FileNotFoundException if the file is not found
     */
    private void readAllPrices(File priceFile) throws FileNotFoundException {

        HashMap<Integer, Provider> addedProviders = new HashMap<>();
        HashMap<String, Procedure> addedProcedures = new HashMap<>();

        CSVReader csvReader = new CSVReader(new FileReader(priceFile));
        Iterator<String[]> it = csvReader.iterator();
        it.next(); //skip the header line
        while (it.hasNext()) {
            String[] nextLine = it.next();
            // nextLine[] is an array of values from the line
            //format: CodeAndNAme0, Provider Id1, Provider Name2, Street Address3, City4, State5, Zip Code6,HRR Description7,Total Discharges8,Average Covered Charges9,Average Total Payments10,Average Medicare Payments11
            String procName = nextLine[0];
            Integer providerID = Integer.parseInt(nextLine[1]);
            String providerName = nextLine[2];
            String providerStreetAddress = nextLine[3];
            String providerCity = nextLine[4];
            String providerState = nextLine[5];
            String providerZIP = nextLine[6];
            double price = Double.parseDouble(nextLine[10]);

            LatLng latLng = latLngMap.get(providerID);
            double lat = latLng.getLat();
            double lng = latLng.getLng();

            //save procedure into database
            Procedure proc = addedProcedures.get(procName);
            if (proc == null) {
                //procedure not added yet
                proc = new Procedure(procName);
                addedProcedures.put(procName, proc);
                mdb.save(proc);
            }

            //save provider into database
            Provider prov = addedProviders.get(providerID);
            if (prov == null) {
                //provider not added yet
                prov = new Provider(providerName, providerID, providerStreetAddress, providerCity, providerState, providerZIP, lat, lng);
                addedProviders.put(providerID, prov);
                mdb.save(prov);
            }

            PriceEntry pe = new PriceEntry(prov, proc, price);
            mdb.save(pe);

        }

    }

    /**
     * Read in price and location data from the files provided.
     *
     * Price csv format: providerID, latitude, longitude
     * Location csv format: CodeAndNAme, Provider Id, Provider Name, Street Address, City, State, Zip Code,HRR Description,Total Discharges,Average Covered Charges,Average Total Payments,Average Medicare Payments
     *
     * @param priceFile     File with procedures and their prices with different providers
     * @param locationFile  File with provider coordinates
     * @throws FileNotFoundException if one of the files is not found
     */
    public void readFromCsv(File priceFile, File locationFile) throws FileNotFoundException {
        readAllLocations(locationFile);
        readAllPrices(priceFile);
    }

    /**
     * wrapper around {@link #readFromCsv(File, File)} to enable reading the files from their filepath strings
     * Price csv format: providerID, latitude, longitude
     * Location csv format: CodeAndNAme, Provider Id, Provider Name, Street Address, City, State, Zip Code,HRR Description,Total Discharges,Average Covered Charges,Average Total Payments,Average Medicare Payments
     *
     * @param priceFileName     path to the price file in resources
     * @param locationFileName  path to the location file in resources
     * @throws FileNotFoundException if one of the files is not found
     */
    public void readFromCsvInResources(String priceFileName, String locationFileName) throws FileNotFoundException {
        File priceFile = loadFile(priceFileName);
        File locationFile = loadFile(locationFileName);
        readFromCsv(priceFile, locationFile);
    }
}
