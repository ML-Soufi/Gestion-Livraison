package ma.gstcmd.order.repositories;

import ma.gstcmd.order.entities.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, Long> {
    OrderEntity findByOrderId(String oderId);
    Boolean existsByOrderId(String orderId);
}
