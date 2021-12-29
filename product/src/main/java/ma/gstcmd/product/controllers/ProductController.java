package ma.gstcmd.product.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.gstcmd.product.dtos.ProductDto;
import ma.gstcmd.product.dtos.ProductDto1;
import ma.gstcmd.product.requests.ProductRequest;
import ma.gstcmd.product.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/products/v1")
public class ProductController {
    private IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"/page/{page}"})
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ProductDto1 getProducts(@PathVariable int page){
        return productService.getProducts(page);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public ProductDto getProduct(@PathVariable String productId){
        return productService.getProduct(productId);
    }

    @GetMapping(path = "/image/{imageName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public @ResponseBody byte[] getProductImage(@PathVariable String imageName) throws IOException {
        return productService.getProductImage(imageName);
    }

    @GetMapping("/exist/{productId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Boolean existProduct(@PathVariable String productId){
        return productService.existProduct(productId);
    }

    @GetMapping("/exist/{productId}/quantity/{orderedQuantity}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public Boolean existProductQuantity(@PathVariable String productId, @PathVariable int orderedQuantity){
        return productService.existProductQuantity(productId, orderedQuantity);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    private ProductDto addProduct(String product, MultipartFile file) throws JsonProcessingException {
        ProductRequest request = new ObjectMapper().readValue(product, ProductRequest.class);
        return productService.addProduct(request, file);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    private ProductDto updateProduct(@PathVariable String productId,@Valid @RequestBody ProductRequest request){
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    private void deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
    }
}
