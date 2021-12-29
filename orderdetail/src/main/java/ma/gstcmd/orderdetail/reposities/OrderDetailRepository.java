package ma.gstcmd.orderdetail.reposities;

import ma.gstcmd.orderdetail.entities.OrderDetailEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetailEntity, Long> {
    List<OrderDetailEntity> findByOrderId(String orderId);
}
