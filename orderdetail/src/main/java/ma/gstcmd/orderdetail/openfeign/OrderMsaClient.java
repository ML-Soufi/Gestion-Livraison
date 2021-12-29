package ma.gstcmd.orderdetail.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "ORDER-SERVICE",  path = "/orders/v1")
public interface OrderMsaClient {
    @GetMapping("/exist/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Boolean existOrder(@PathVariable String orderId);
}
