package ma.gstcmd.orderdetail.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(value = "PRODUCT-SERVICE", path = "/products/v1")
public interface ProductMsaClient {
    @GetMapping("/exist/{productId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    Boolean existProduct(@PathVariable String productId);

    @GetMapping("/exist/{productId}/quantity/{orderedQuantity}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    Boolean existProductQuantity(@PathVariable String productId, @PathVariable int orderedQuantity);
}
