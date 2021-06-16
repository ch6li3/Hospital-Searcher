package ac.uk.dundee.agile7.repository;

import ac.uk.dundee.agile7.entity.PriceEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface PriceEntryRepository extends CrudRepository<PriceEntry, Long> {

    Collection<PriceEntry> findAllByProcedureNameContaining(@Param("query") String query);

}
