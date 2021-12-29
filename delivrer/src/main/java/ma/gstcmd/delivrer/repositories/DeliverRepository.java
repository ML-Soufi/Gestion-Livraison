package ma.gstcmd.delivrer.repositories;

import ma.gstcmd.delivrer.entities.DeliverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverRepository extends PagingAndSortingRepository<DeliverEntity, Long> {
    DeliverEntity findByDeliverId(String deliverId);
    @Query(value = "SELECT d FROM Delivers d WHERE LOWER(d.firstName) LIKE %?1%")
    Page<DeliverEntity> findByFirstNameContains(String firstName, Pageable pageable);
    Boolean existsByDeliverId(String deliverId);
}
