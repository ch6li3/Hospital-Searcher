package ac.uk.dundee.agile7.service;

import ac.uk.dundee.agile7.DataLoader;
import ac.uk.dundee.agile7.PriceEntryOutput;
import ac.uk.dundee.agile7.entity.PriceEntry;
import ac.uk.dundee.agile7.repository.PriceEntryRepository;
import ac.uk.dundee.agile7.repository.ProcedureRepository;
import ac.uk.dundee.agile7.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @Autowired
    private DataLoader dataLoader;


    public static final String EXAMPLE_PRICE_CSV = "example_prices.csv";
    public static final String EXAMPLE_LOCATION_CSV = "example_locations.csv";


    @BeforeEach
    public void setUp() {
        searchService.deleteAll();
        try {
            dataLoader.readFromCsvInResources(EXAMPLE_PRICE_CSV, EXAMPLE_LOCATION_CSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateReps() {
        ProviderRepository pvr = searchService.getProviderRepository();
        ProcedureRepository pcr = searchService.getProcedureRepository();
        PriceEntryRepository per = searchService.getPriceEntryRepository();
    }

    @Test
    public void testCreateRepsNotNull() {
        assertNotNull(searchService.getPriceEntryRepository());
        assertNotNull(searchService.getProcedureRepository());
        assertNotNull(searchService.getProviderRepository());
    }


    @Test
    public void testQueryLikeNotEmpty() {

        assertThat(searchService.getPriceEntryRepository().findAll()).isNotEmpty();
        Collection<PriceEntry> found = searchService.searchForProcedureLike("101");
        assertNotEquals(0, found.size());
    }

    @Test
    public void testQueryLike() {
        assertThat(searchService.getPriceEntryRepository().findAll()).isNotEmpty();
        Collection<PriceEntry> found = searchService.searchForProcedureLike("101");

        boolean pass = false;
        for (PriceEntry pe : found) {
            if (pe.getProvider().getProviderID() == 10001) {
                pass = true;
                break;
            }
        }
        assertTrue(pass);
    }

    @Test
    public void testQueryLikeWithLocationAndPrice() {

        Collection<PriceEntryOutput> found = searchService.searchForProcedureLike("101", 31.215392, -85.363373, 10, 100000, SearchService.getDistanceSorter());
        assertThat(found).hasSize(1);

        PriceEntryOutput first = (PriceEntryOutput) found.toArray()[0];
        assertThat(first.getProviderName()).isEqualTo("SOUTHEAST ALABAMA MEDICAL CENTER");

    }


    @Test
    public void testSortByDistance() {

        List<PriceEntryOutput> found = 
                searchService.searchForProcedureLike("101", 31.215392, -85.363373, 10000, 100000, SearchService.getDistanceSorter());

        for(int i = 0; i< found.size()-1; ++i) {
            assertThat(found.get(i).getDistance()).isLessThanOrEqualTo((found.get(i+1)).getDistance());
        }
    }

    @Test
    public void testSortByPriceDesc() {
        List<PriceEntryOutput> found =
                searchService.searchForProcedureLike("101", 31.215392, -85.363373, 10000, 100000, SearchService.getPriceDescSorter());


        for(int i = 0; i< found.size()-1; ++i) {
            assertThat(found.get(i).getPrice()).isGreaterThanOrEqualTo((found.get(i+1)).getPrice());
        }
    }

    @Test
    public void testSortByPriceAsc() {
        List<PriceEntryOutput> found =
                searchService.searchForProcedureLike("101", 31.215392, -85.363373, 10000, 100000, SearchService.getPriceAscSorter());

        for(int i = 0; i< found.size()-1; ++i) {
            assertThat(found.get(i).getPrice()).isLessThanOrEqualTo((found.get(i+1)).getPrice());
        }
    }
}
