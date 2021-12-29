package ma.gstcmd.apigateway.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ma.gstcmd.apigateway.exceptions.ExceptionTemplate;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RefreshScope

@Component
public class JwtFilter implements GatewayFilter {
    private RouteValidator routeValidator;

    @Autowired
    public JwtFilter(RouteValidator routeValidator) {
        this.routeValidator = routeValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        try {
            ServerHttpRequest request = exchange.getRequest();
            if (routeValidator.isSecured.test(request)) {
                if(request.getPath().toString().contains("/PRODUCT-SERVICE/image/"))
                    return chain.filter(exchange);
                String token = this.extractJWTToken(exchange,request);
                this.validateToken(token);
                return chain.filter(exchange);
            }
            return chain.filter(exchange);
        }catch (Exception ex) {
            try {
                return onError(exchange, ex.getMessage(), HttpStatus.BAD_REQUEST);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) throws JsonProcessingException {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(httpStatus);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            ExceptionTemplate template = new ExceptionTemplate(httpStatus.value(), err, new Date());
            byte[] bytes = template.toString().getBytes(StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            return response.writeWith(Mono.just(new DefaultDataBufferFactory().wrap(objectMapper.writeValueAsBytes(template))));
    }

    private String extractJWTToken(ServerWebExchange exchange, ServerHttpRequest request) throws Exception {
        if (!request.getHeaders().containsKey("Authorization"))
            throw new Exception("Authorization header n'éxiste pas.");


        List<String> headers = request.getHeaders().get("Authorization");
        if (headers.isEmpty())
            throw new Exception("Authorization header est vide.");

        String credential = headers.get(0).trim();
        String[] components = credential.split("\\s");

        if (components.length != 2)
            throw new Exception("Le contenu Authorization header est  mal écrit.");

        if (!components[0].equals("Bearer"))
            throw new Exception("Le contenu Authorization header ne contient pas Bearer.");

        // check if Authorization content 2 part : include jwt
        if(components[1].trim().chars().filter(ch -> ch == '.').count() != 2)
            throw new Exception("Le contenu du JWT est mal écrit.");

        // check if Authorization content 2 part : include a valid jwt
        String[] parts = components[1].split("\\.");
        if(parts[0].length() == 0 || parts[1].length() == 0 || parts[2].length() == 0)
            throw new Exception("Le contenu du JWT est mal écrit.");

        return components[1].trim();
    }

    private String validateToken(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256("P@5sw0rd");
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        if(decodedJWT.getExpiresAt().before(new Date()))
            throw new Exception("Token is expired.");
        return "done";
    }
}
