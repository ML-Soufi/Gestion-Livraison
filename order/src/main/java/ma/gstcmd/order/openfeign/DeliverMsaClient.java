package ma.gstcmd.order.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;


@FeignClient( name = "DELIVER-SERVICE", path = "/delivers/v1")
public interface DeliverMsaClient {
    @GetMapping("/exist/{deliverId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    Boolean existDeliver(@PathVariable String deliverId);
}

