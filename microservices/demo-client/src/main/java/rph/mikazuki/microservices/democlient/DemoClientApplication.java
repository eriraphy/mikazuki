package rph.mikazuki.microservices.democlient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DemoClientApplication {

    @Value("${common.env}")
    private String env;

    public static void main(String[] args) {
        SpringApplication.run(DemoClientApplication.class, args);
    }

    @RequestMapping("/")
    public String greetings(){
        return "Hi there! This is a demo client.";
    }

    @RequestMapping("/env")
    public String env() {
        return "Current env: " + env;
    }
}
