package ac.uk.dundee.agile7.repository;

import ac.uk.dundee.agile7.entity.Procedure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProcedureRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Test
    public void test_whenFindByName_thenReturnEmployee() {
        // given
        Procedure proc = new Procedure("nice");
        entityManager.persist(proc);
        entityManager.flush();

        // when
        Procedure found = procedureRepository.findByName(proc.getName());

        // then
        assertThat(found.getName())
                .isEqualTo(proc.getName());
    }


    @Test
    public void testBasicInsert() {
        procedureRepository.save(new Procedure("012 procedure"));
    }
    @Test
    public void testInsert() {
        Procedure inserted = new Procedure("myProcedure");
        procedureRepository.save(inserted);

        Procedure foundOld = procedureRepository.findByName("test");
        assertThat(foundOld).isNull();

        Procedure foundNew = procedureRepository.findByName("myProcedure");
        assertThat(foundNew).isEqualTo(inserted);
    }
    @Test
    public void testQueryLike() {
        Procedure proc1 = new Procedure("hello123");
        Procedure proc2 = new Procedure("hel123lo");
        Procedure proc3 = new Procedure("hello456");
        procedureRepository.save(proc1);
        procedureRepository.save(proc2);
        procedureRepository.save(proc3);

        Collection<Procedure> found = procedureRepository.findAllByNameContaining("123");
        assertThat(found).hasSize(2);
        assertThat(found).contains(proc1);
        assertThat(found).contains(proc2);
        assertThat(found).doesNotContain(proc3);

    }

    @Test
    public void testDelete() {
        Procedure procedureOrg = new Procedure("delete");

        Procedure save = procedureRepository.save(procedureOrg);

        Procedure procedure = procedureRepository.findByName(procedureOrg.getName());
        assertThat(procedure.getId()).isEqualTo(procedureOrg.getId());

        procedureRepository.deleteById(procedureOrg.getId());
        procedure = procedureRepository.findByName(procedureOrg.getName());
        assertThat(procedure).isNull();
    }
    @Test
    public void testSave(){
        Procedure proc = new Procedure("something");
        procedureRepository.save(proc);

        Procedure procedure = procedureRepository.findByName(proc.getName());
        assertThat(procedure.getName()).isEqualTo(procedure.getName());
    }
}