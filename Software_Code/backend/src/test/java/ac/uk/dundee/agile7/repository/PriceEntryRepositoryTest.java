package ac.uk.dundee.agile7.repository;

import ac.uk.dundee.agile7.entity.PriceEntry;
import ac.uk.dundee.agile7.entity.Procedure;
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
class PriceEntryRepositoryTest {
    @Autowired
    private PriceEntryRepository priceEntryRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Test
    public void testPriceEntry(){
        priceEntryRepository.save(new PriceEntry());
    }

    @Test
    public void testWithProc() {
        Procedure proc1 = new Procedure("myProcedure");
        Provider prov1 = new Provider("Gary's hospital");

        PriceEntry pe1 = new PriceEntry();
        pe1.setPrice(100);
        pe1.setProcedure(proc1);
        pe1.setProvider(prov1);
        priceEntryRepository.save(pe1);

        Optional<PriceEntry> found = priceEntryRepository.findById(pe1.getId());
        assertTrue(found.isPresent());
        assertEquals(pe1, found.get());
        assertEquals(proc1, found.get().getProcedure());
        assertEquals(prov1, found.get().getProvider());
    }

    @Test
    public void testAddOneProcedureManyProvider() {

        Procedure proc1 = new Procedure("myProcedure");
        Provider prov1 = new Provider("Gary's hospital");
        Provider prov2 = new Provider("Fred's hospital");

        PriceEntry pe1 = new PriceEntry(prov1, proc1, 100);
        priceEntryRepository.save(pe1);

        PriceEntry pe2 = new PriceEntry(prov2, proc1, 79);
        priceEntryRepository.save(pe2);



        Optional<PriceEntry> found = priceEntryRepository.findById(pe1.getId());
        Optional<PriceEntry> found2 = priceEntryRepository.findById(pe2.getId());
        assertTrue(found.isPresent());
        assertTrue(found2.isPresent());
        assertEquals(proc1, found.get().getProcedure());
        assertEquals(prov1, found.get().getProvider());
        assertEquals(proc1, found2.get().getProcedure());
        assertEquals(prov2, found2.get().getProvider());
    }

    @Test
    public void testFindByProcedure() {

        Procedure proc1 = new Procedure("myProcedure");
        Procedure procDifferent = new Procedure("other procedure");
        Provider prov1 = new Provider("Gary's hospital");
        Provider prov2 = new Provider("Fred's hospital");
        procedureRepository.save(proc1);
        procedureRepository.save(procDifferent);
        providerRepository.save(prov1);
        providerRepository.save(prov2);

        PriceEntry pe1 = new PriceEntry(prov1, proc1, 100);
        priceEntryRepository.save(pe1);

        PriceEntry pe2 = new PriceEntry(prov2, proc1, 79);
        priceEntryRepository.save(pe2);

        PriceEntry peDiff = new PriceEntry(prov1, procDifferent, 999);
        priceEntryRepository.save(peDiff);

        int check = 0;
        for (PriceEntry found : priceEntryRepository.findAllByProcedureNameContaining(proc1.getName())) {
            assertEquals(proc1, found.getProcedure());
            if (found.getProvider().equals(prov1))  {
                assertEquals(prov1, found.getProvider());
                check +=1;
            } else if (found.getProvider().equals(prov2)) {
                assertEquals(prov2, found.getProvider());
                check +=2;
            } else {
                fail();
            }
        }

        assertEquals(3, check);
    }


}