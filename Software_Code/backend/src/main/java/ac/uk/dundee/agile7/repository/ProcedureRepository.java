package ac.uk.dundee.agile7.repository;

import ac.uk.dundee.agile7.entity.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface ProcedureRepository extends CrudRepository<Procedure, Long> {


    void deleteById(Long id);

    Procedure findByName(String name);

    Collection<Procedure> findAllByNameContaining(String entered);
}
