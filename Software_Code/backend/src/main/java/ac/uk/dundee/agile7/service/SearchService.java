package ac.uk.dundee.agile7.service;

import ac.uk.dundee.agile7.PriceEntryOutput;
import ac.uk.dundee.agile7.entity.PriceEntry;
import ac.uk.dundee.agile7.entity.Procedure;
import ac.uk.dundee.agile7.entity.Provider;
import ac.uk.dundee.agile7.repository.PriceEntryRepository;
import ac.uk.dundee.agile7.repository.ProcedureRepository;
import ac.uk.dundee.agile7.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {

    private final PriceEntryRepository peRep;
    private final ProviderRepository providerRep;
    private final ProcedureRepository procRep;

    private static Comparator<PriceEntryOutput> comparatorDistanceAsc = Comparator.comparing(PriceEntryOutput::getDistance);

    private static Comparator<PriceEntryOutput> comparatorPriceAsc = Comparator.comparing(PriceEntryOutput::getPrice);
    private static Comparator<PriceEntryOutput> comparatorPriceDesc = Comparator.comparing(PriceEntryOutput::getPrice, Comparator.reverseOrder());

    public SearchService(PriceEntryRepository peRep, ProviderRepository providerRep, ProcedureRepository procRep) {
        this.peRep = peRep;
        this.providerRep = providerRep;
        this.procRep = procRep;
    }

    /**
     * Deletes all entries in all tables
     */
    public void deleteAll() {
        peRep.deleteAll();
        procRep.deleteAll();
        providerRep.deleteAll();
    }

    /**
     * Get a list of procedure names containing the String, limited to a certain number of results.
     * @param auto  String to autocomplete from
     * @param max   max number of results
     * @return A list of procedure names containing the string
     */
    public Collection<String> autocompleteProcedure(String auto,int max){
        Collection<Procedure> complete = procRep.findAllByNameContaining(auto);
        Collection<String> list = new ArrayList<>();

        Iterator<Procedure> iterator = complete.iterator();
        int i =0;
        while (iterator.hasNext() && i < max) {
            Procedure c = iterator.next();
            ++i;
            String suggestion = c.getName();
            list.add(suggestion);
        }

        return list;
    }

    /**
     * Filter results received from the database into a list of PriceEntry output objects filtered by distance and price
     * @param allFound  Collection of all PriceEntries found in the database
     * @param userlat   user's latitude
     * @param userlng   user's longitude
     * @param maxDistance   maximum distance for filtering
     * @param maxPrice  maximum price for filtering
     * @return  a list of PriceEntryOutputs filtered as desired
     */
    private List<PriceEntryOutput> filterDbResults(Collection<PriceEntry> allFound, double userlat, double userlng, double maxDistance, double maxPrice) {
        List<PriceEntryOutput> filtered = new ArrayList<>();
        final double MILES_IN_DEG = 69;
        for (PriceEntry pe: allFound) {
            double latDiff = pe.getProvider().getLat() - userlat;
            double lngDiff = pe.getProvider().getLng() - userlng;
            double degDiff = Math.sqrt(Math.abs(latDiff * latDiff + lngDiff * lngDiff)); //distance formula (dist = sqrt((x1-x2)^2 + (y1-y2)^2))
            double calDis = degDiff* MILES_IN_DEG;
            if (calDis <= maxDistance && pe.getPrice() <= maxPrice) {
                filtered.add(new PriceEntryOutput(pe, calDis));
            }
        }
        return filtered;
    }

    /**
     * Sort the results with the inputted comparator, or by distance if null
     * @param toSort    List to sort
     * @param sorter    Comparator using which to sort
     */
    private void sortResults(List<PriceEntryOutput> toSort, Comparator<PriceEntryOutput> sorter) {
        if (sorter == null)
            sorter = getDistanceSorter();
        //could also check the sorter is one of the three that we want to allow but that limits functionality
        toSort.sort(sorter);
    }

    /**
     * Get a procedure with a name containing the query from the database, with location and price filters.
     *
     * @param query     Procedure name to match
     * @param userlat   user latitude
     * @param userlng   user longitude
     * @param distance  maximum distance to filter by
     * @param maxPrice  maximum price
     * @param sorter    comparator to sort the results by - preferably use one of the static ones in this class
     * @return A Collection of PriceEntryOutput objects matching the search criteria
     */
    public List<PriceEntryOutput> searchForProcedureLike(String query, double userlat, double userlng, double distance, double maxPrice, Comparator<PriceEntryOutput> sorter) {
        Collection<PriceEntry> allWithName = peRep.findAllByProcedureNameContaining(query);
        List<PriceEntryOutput> filtered = filterDbResults(allWithName, userlat, userlng, distance, maxPrice);

        sortResults(filtered, sorter);

        return filtered;
    }

    /**
     * Get the comparator to specify the sorting order in searchForProcedureLike(). Return the distance comparator if string null or nothing matches
     * Sorting order options: distance, priceAsc, priceDesc
     *
     * @param sortingOrder sorting order as a string, see function description for options.
     * @return the comparator to specify the sorting order
     */
    public static Comparator<PriceEntryOutput> getComparatorFromString(String sortingOrder) {
        if (sortingOrder == null || sortingOrder.equals("distance"))
            return getDistanceSorter();
        else if (sortingOrder.equals("priceAsc"))
            return getPriceAscSorter();
        else if (sortingOrder.equals("priceDesc"))
            return getPriceDescSorter();
        else
            return getDistanceSorter();
    }


    public Collection<PriceEntry> searchForProcedureLike(String query) {
        return peRep.findAllByProcedureNameContaining(query);
    }

    public static Comparator<PriceEntryOutput> getDistanceSorter() {
        return comparatorDistanceAsc;
    }

    public static Comparator<PriceEntryOutput> getPriceAscSorter() {
        return comparatorPriceAsc;
    }

    public static Comparator<PriceEntryOutput> getPriceDescSorter() {
        return comparatorPriceDesc;
    }

    public ProcedureRepository getProcedureRepository() {
        return procRep;
    }

    public ProviderRepository getProviderRepository() {
        return providerRep;
    }

    public PriceEntryRepository getPriceEntryRepository() {
        return peRep;
    }


    public void save(Provider pv) {
        providerRep.save(pv);
    }
    public void save(Procedure pc) {
        procRep.save(pc);
    }
    public void save(PriceEntry pe) {
        peRep.save(pe);
    }

}
