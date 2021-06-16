package ac.uk.dundee.agile7.repository;

import ac.uk.dundee.agile7.entity.Provider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ProviderRepositoryTest {

    @Autowired
    ProviderRepository providerRepository;

    @Test
    public void testInsert() {
        providerRepository.save(new Provider());
    }

    @Test
    public void testSaveAndFindById() {
        Provider prov1 = new Provider("Gary's hospital");
        prov1.setStreetAddress("123 Main St");
        prov1.setCity("New York");
        prov1.setPostcode("12345");
        prov1.setState("AZ");
        prov1.setLat(61.33f);
        prov1.setLng(32.78f);
        providerRepository.save(prov1);

        Optional<Provider> found = providerRepository.findById(prov1.getId());
        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(prov1, found.get());

    }
    @Test
    public void testNotFind() {
        Provider prov1 = new Provider("Gary's hospital",12345, "123 Main St", "New York", "AZ", "12345", 61.33f, 32.78f);
        providerRepository.save(prov1);

        Optional<Provider> found = providerRepository.findById(prov1.getId() +1);

        assertFalse(found.isPresent());
    }

    @Test
    public void testSaveMany() {
        for (int i = 0; i < 10; ++i) {
            providerRepository.save(new Provider("Provider " + i));
        }

        assertEquals(10, providerRepository.count());
    }

    @Test
    public void testConstruct() {
        Provider prov1 = new Provider("Gary's hospital", 12345, "123 Main St", "New York", "AZ", "12345", 61.33f, 32.78f);
        providerRepository.save(prov1);

        long id1 = prov1.getId();
        System.out.println("Looking for provider with ID: "+id1);
        Optional<Provider> found = providerRepository.findById(id1);

        assertNotNull(found);
        assertTrue(found.isPresent());
        assertEquals(prov1, found.get());
    }
}