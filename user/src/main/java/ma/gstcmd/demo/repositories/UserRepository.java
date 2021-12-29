package ma.gstcmd.demo.repositories;

import ma.gstcmd.demo.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId);
    @Query(value = "SELECT u FROM Users u WHERE LOWER(u.userLastName) LIKE %?1%")
    Page<UserEntity> findByUserLastNameIsContaining(String userLastName, Pageable pageable);
    Boolean existsByUserId(String userId);
}
