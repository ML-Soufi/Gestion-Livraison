package ma.gstcmd.apigateway.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.gstcmd.apigateway.exceptions.ExceptionTemplate;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
public class ApiGatewayController {
    @GetMapping("fallback")
    public Mono<ExceptionTemplate> fallback(){
        String msg = "ce service n'est pas disponnible pour le moment.";
        ExceptionTemplate template = new ExceptionTemplate(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, new Date());
        return Mono.just(template);
    }
}
