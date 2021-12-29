package ma.gstcmd.order.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "CLIENT-SERVICE", path = "/clients/v1")
public interface ClientMsaClient {
    @GetMapping("/exist/{clientId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    Boolean existClient(@PathVariable String clientId);
}
