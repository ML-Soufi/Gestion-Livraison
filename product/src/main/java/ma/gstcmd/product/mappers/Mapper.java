package ma.gstcmd.product.mappers;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ma.gstcmd.product.dtos.ProductDto;
import ma.gstcmd.product.entities.ProductEntity;
import ma.gstcmd.product.requests.ProductRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public ProductDto EntityToDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(productEntity, productDto);
        return productDto;
    }

    public List<ProductDto> EntityToDto(List<ProductEntity> productEntities) {
        List<ProductDto> productDtoList = new ArrayList<>();
        productEntities.forEach(productEntity -> productDtoList.add(EntityToDto(productEntity)));
        return productDtoList;
    }

    public ProductEntity RequestToEntity(ProductRequest request) {
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(request, productEntity);
        return productEntity;
    }
}
