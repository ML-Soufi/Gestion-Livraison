package ma.gstcmd.orderdetail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderDetailApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderDetailApplication.class, args);
    }

}
