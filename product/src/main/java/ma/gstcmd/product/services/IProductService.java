package ma.gstcmd.product.services;

import ma.gstcmd.product.dtos.ProductDto;
import ma.gstcmd.product.dtos.ProductDto1;
import ma.gstcmd.product.requests.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface IProductService {
    ProductDto1 getProducts(int page);
    ProductDto getProduct(String productId);
    byte[] getProductImage(String imageName) throws IOException;
    Boolean existProduct(String productId);
    Boolean existProductQuantity(String productId, int orderedQuantity);
    ProductDto addProduct(ProductRequest request, MultipartFile file);
    ProductDto updateProduct(String productId, ProductRequest request);
    Boolean updateProductQuantity(String productId, int orderedQuantity);
    void deleteProduct(String productId);
}
