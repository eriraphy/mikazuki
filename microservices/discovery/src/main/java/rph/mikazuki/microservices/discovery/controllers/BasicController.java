package rph.mikazuki.microservices.discovery.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @RequestMapping("/")
    public String index(){
        return "Hi there from Eureka server";
    }
}
