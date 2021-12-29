package ma.gstcmd.apigateway.configurations;

import ma.gstcmd.apigateway.filters.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    private JwtFilter filter;

    @Autowired
    public GatewayConfiguration(JwtFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                // filter User Msa request
                .route((r) -> r.path("/USER-SERVICE/**")
                        .filters(f -> f
                                .filter(filter)
                                .rewritePath("/USER-SERVICE/(?<segment>/?.*)","/users/v1/${segment}"))
                        .uri("lb://USER-SERVICE"))

                // filter Client Msa request
                .route((r) -> r.path("/CLIENT-SERVICE/**")
                        .filters(f -> f
                                .filter(filter)
                                .rewritePath("/CLIENT-SERVICE/(?<segment>/?.*)","/clients/v1/${segment}")
                        )
                        .uri("lb://CLIENT-SERVICE"))

                // filter Order Msa request
                .route((r) -> r.path("/ORDER-SERVICE/**")
                        .filters(f -> f
                                .filter(filter)
                                .rewritePath("/ORDER-SERVICE/(?<segment>/?.*)","/orders/v1/${segment}"))
                        .uri("lb://ORDER-SERVICE"))

                // filter Order Msa request
                .route((r) -> r.path("/ORDERDETAIL-SERVICE/**")
                        .filters(f -> f
                                .filter(filter)
                                .rewritePath("/ORDERDETAIL-SERVICE/(?<segment>/?.*)","/orderdetails/v1/${segment}"))
                        .uri("lb://ORDERDETAIL-SERVICE"))

                // filter Product Msa request
                .route((r) -> r.path("/PRODUCT-SERVICE/**")
                        .filters(f -> f
                                .filter(filter)
                                .rewritePath("/PRODUCT-SERVICE/(?<segment>/?.*)","/products/v1/${segment}")
                        )
                        .uri("lb://PRODUCT-SERVICE"))

                // filter Deliver Msa request
                .route((r) -> r.path("/DELIVER-SERVICE/**")
                        .filters(f -> f
                                .filter(filter)
                                .rewritePath("/DELIVER-SERVICE/(?<segment>/?.*)","/delivers/v1/${segment}"))
                        .uri("lb://DELIVER-SERVICE"))
                .build();
    }
}
