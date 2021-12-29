package ma.gstcmd.client.repositories;

import ma.gstcmd.client.entities.ClientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, Long> {
    ClientEntity findByClientId(String clientId);
    Boolean existsByClientId(String clientId);
}
