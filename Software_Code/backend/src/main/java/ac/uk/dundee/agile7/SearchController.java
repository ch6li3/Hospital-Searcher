package ac.uk.dundee.agile7;

import ac.uk.dundee.agile7.service.SearchService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {


    final SearchService mdb;

    public SearchController(SearchService mdb) {
        this.mdb = mdb;
    }


    // usage: /search?q=[query]&userlat=[user_latitude]&userlng=[user_longitude]&distance=[distance]&maxPrice=[maxPrice]

    /**
     * Get a procedure with a name containing the query from the database, with location and price filters.
     *
     * usage: /search?q=[query]&userlat=[user_latitude]&userlng=[user_longitude]&distance=[distance]&maxPrice[maxPrice]&sortBy=[order]
     *
     * The sorting order is one of: priceAsc, priceDesc, distance.
     *
     * The query must be at least 3 characters long to limit the number of procedure returned. If it is then null is returned.
     *
     * @param query     Procedure name to match
     * @param userlat   user latitude
     * @param userlng   user longitude
     * @param distance  maximum distance to filter by
     * @param maxPrice  maximum price
     * @param sortBy    what to sort by. See above for options.
     * @return A List of PriceEntryOutput objects matching the search criteria
     */
    @GetMapping("/search")
    public List<PriceEntryOutput> search(@RequestParam(value = "q") String query,
                                   @RequestParam(value = "userlat",required = false) Double userlat,
                                   @RequestParam(value = "userlng",required = false) Double userlng,
                                   @RequestParam(value = "distance") Double distance,
                                   @RequestParam(value = "maxPrice") Double maxPrice,
                                   @RequestParam(value = "sortBy", required = false, defaultValue = "distance") String sortBy) {
        //should return a list of priceEntries where procedure has name including procedure name
        if (query == null) {
            System.out.println("No search query entered");
            return new ArrayList<>();
        } else if (query.length() < 3) {
            System.out.println("Search query less than 3 characters");
            return new ArrayList<>();
        }
        else {
            query = query.toUpperCase();
            System.out.println("Searching for query like: " + query);
            if(userlat == null ||userlng == null) {
                System.out.println("No user location entered,setting location and distance to defaults");
                userlat = 32.00;
                userlng = -82.00;
                distance = 1000000.00;
            }


            Comparator<PriceEntryOutput> sorter = SearchService.getComparatorFromString(sortBy);
            return mdb.searchForProcedureLike(query, userlat, userlng, distance, maxPrice, sorter);
        }

    }
    // usage: /autocomplete?a=[proc]
    @GetMapping("/autocomplete")
    public List<String> autocomplete(@RequestParam(value = "a")String proc,@RequestParam(value = "max", required = false) Integer max){
        if(proc == null) {
            System.out.println("Nothing Found");
            return new ArrayList<>();

        }
        else if (proc.length()<3)
        {
            System.out.println("Search procedure should be more than 3 characters");
            return new ArrayList<>();
        }
        else{
            proc = proc.toUpperCase();
            System.out.println("Search for procedure: " + proc);
            if (max==null){
                max = 6;
            }
            return (List<String>) mdb.autocompleteProcedure(proc,max);
        }
    }
}
