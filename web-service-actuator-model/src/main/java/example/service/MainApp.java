package example.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) {
    	//if(args.length < 4) {
    		String serviceArgs[] = new String[4];
    		serviceArgs[0] ="http://localhost:8000/put?serviceName=";
    		serviceArgs[1] ="actuator-name";
    		serviceArgs[2] ="http://localhost";
    		serviceArgs[3] = "actuator";
    		System.out.print("\n I'M PASSING THIS NUMBER OF ARGS: ");
    		System.out.println(serviceArgs.length);
    	//	Where this microservice will be connecting to, this service name, this service uri, type of service
    		SpringApplication.run(MainApp.class, serviceArgs);
    	//}
    	//else{
    		//SpringApplication.run(MainApp.class, args);
    	//}
    }
}
