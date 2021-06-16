package ac.uk.dundee.agile7;

import ac.uk.dundee.agile7.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataLoaderTest {

    @Autowired
    DataLoader dataLoader;

    @Autowired
    SearchService searchService;

    public static final String EXAMPLE_PRICE_CSV = "example_prices.csv";
    public static final String EXAMPLE_LOCATION_CSV = "example_locations.csv";

    @BeforeEach
    public void setUp() {
        searchService.deleteAll();
    }

    @Test
    public void testReadLocations() {
        try {
            dataLoader.readAllLocations(EXAMPLE_LOCATION_CSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNotNull(dataLoader.getLatLngAt(10005));
    }

    @Test
    public void testReadExampleCSV() {
        try {
            dataLoader.readFromCsvInResources(EXAMPLE_PRICE_CSV, EXAMPLE_LOCATION_CSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assertNotEquals(0, searchService.getPriceEntryRepository().count());
        assertNotEquals(0, searchService.getProcedureRepository().count());
        assertNotEquals(0, searchService.getProviderRepository().count());
    }

    @Test
    public void testEnsureCount() {
        try {
            dataLoader.readFromCsvInResources(EXAMPLE_PRICE_CSV, EXAMPLE_LOCATION_CSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //sizes of example files
        assertEquals(410, searchService.getPriceEntryRepository().count());
        assertEquals(6, searchService.getProviderRepository().count());
    }
}