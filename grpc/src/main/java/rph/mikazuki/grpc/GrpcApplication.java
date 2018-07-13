package rph.mikazuki.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import rph.mikazuki.grpc.demo.DemoService;

@SpringBootApplication
@EnableEurekaClient
public class GrpcApplication {

	@Bean
	public DemoService demoService(){
		return new DemoService();
	}

	public static void main(String[] args) {
		SpringApplication.run(GrpcApplication.class, args);
	}
}
