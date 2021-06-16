package ac.uk.dundee.agile7.repository;

import ac.uk.dundee.agile7.entity.Provider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends CrudRepository<Provider, Long> {
    Provider findByName(String name);

    Provider findByProviderID(int providerID);
}
