package example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
    	if(args.length < 4) {
    		String serviceArgs[] = {"http://localhost:8000/put?serviceName=", "service-name", "http://localhost", "sensor"};
    	//	Where this microservice will be connecting to, this service name, this service uri, type of service
    		SpringApplication.run(MainApp.class, serviceArgs);
    	}
    	else{
    		SpringApplication.run(MainApp.class, args);
    	}
    }
}
