package teamspace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@Slf4j
public class GatewayDiscoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayDiscoveryServiceApplication.class, args);
    }

}
