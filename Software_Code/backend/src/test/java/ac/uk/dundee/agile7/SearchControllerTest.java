package ac.uk.dundee.agile7;

import ac.uk.dundee.agile7.entity.PriceEntry;
import ac.uk.dundee.agile7.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchControllerTest {
    @Autowired
    SearchService mdb;

    @Autowired
    DataLoader dataLoader;

    public void clearDb() {

        mdb.deleteAll();
        try {
            dataLoader.readFromCsvInResources(DataLoaderTest.EXAMPLE_PRICE_CSV, DataLoaderTest.EXAMPLE_LOCATION_CSV);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRequestContains101() {
        clearDb();
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/api/search?q=101&userlat=30&userlng=-80&distance=3000000&maxPrice=10000000",
                String.class)).contains("101");
    }

    @Test
    public void testQuerySize() {
        clearDb();
        PriceEntry[] result = this.restTemplate.getForObject("http://localhost:" + port + "/api/search?q=101&userlat=30&userlng=-80&distance=3000000&maxPrice=100000000",
                PriceEntry[].class);
        System.out.println("result size in testQuerySize is " + result.length);
        assertThat(result).hasSize(4);
    }

    @Test
    public void testSmallRangeQuery() {
        clearDb();
        PriceEntryOutput[] result =
                this.restTemplate.getForObject("http://localhost:" + port + "/api/search?q=101&userlat=31.215392&userlng=-85.363373&distance=10&maxPrice=1000000",
                        PriceEntryOutput[].class);
        assertThat(result).hasSize(1);
        assertThat((result[0].getProviderName())).isEqualTo("SOUTHEAST ALABAMA MEDICAL CENTER");
    }

    @Test
    public void testSmallRangeQueryDoesNotContain() {
        clearDb();
        PriceEntryOutput[] result =
                this.restTemplate.getForObject("http://localhost:" + port + "/api/search?q=101&userlat=31.215392&userlng=-85.363373&distance=10&maxPrice=1000000",
                        PriceEntryOutput[].class);
        assertThat(result).hasSize(1);
        assertThat((result[0].getProviderName())).isNotEqualTo("MARSHALL MEDICAL CENTER SOUTH");
    }

    @Test
    public void testAutocomplete() {
        clearDb();
        String[] result =
                this.restTemplate.getForObject("http://localhost:" + port + "/api/autocomplete?a=Cranio",
                        String[].class);
        for(String res:result){
            assertThat(res).contains("CRANIO");
        }
    }
    @Test
    public void testLessThanSevenResults(){
        clearDb();
        String[] result =
                this.restTemplate.getForObject("http://localhost:" + port + "/api/autocomplete?a=CHE",
                        String[].class);
        assertThat(result).hasSizeLessThan(7);
    }

    @Test
    public void testSortByDistance() {
        clearDb();
        PriceEntryOutput[] result =
                this.restTemplate.getForObject
                        ("http://localhost:" + port + "/api/search?q=101&userlat=31.215392&userlng=-85.363373&distance=500&maxPrice=1000000&sortBy=distance",
                        PriceEntryOutput[].class);
        assertThat(result).isSortedAccordingTo(SearchService.getDistanceSorter());
    }
    @Test
    public void testSortByPriceAsc() {
        clearDb();
        PriceEntryOutput[] result =
                this.restTemplate.getForObject
                        ("http://localhost:" + port + "/api/search?q=101&userlat=31.215392&userlng=-85.363373&distance=500&maxPrice=1000000&sortBy=priceAsc",
                                PriceEntryOutput[].class);
        assertThat(result).isSortedAccordingTo(SearchService.getPriceAscSorter());
    }
    @Test
    public void testSortByPriceDesc() {
        clearDb();
        PriceEntryOutput[] result =
                this.restTemplate.getForObject
                        ("http://localhost:" + port + "/api/search?q=101&userlat=31.215392&userlng=-85.363373&distance=500&maxPrice=1000000&sortBy=priceDesc",
                                PriceEntryOutput[].class);
        assertThat(result).isSortedAccordingTo(SearchService.getPriceDescSorter());
    }
}