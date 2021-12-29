package ma.gstcmd.product.repositories;

import ma.gstcmd.product.entities.ProductEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity, Long> {
    ProductEntity findByProductId(String productId);
    Boolean existsByProductId(String productId);
}
