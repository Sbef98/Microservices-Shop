MICROSERVICES TO SETUP AND TRY OUT:

    - Basic context generator (time, date...)
    
    - Weather
    
    - Customer context generator (number of people in the shop, average age, %of genders*...)
    
    - Binary Actuator(ON/OFF)
    
    - WORD long actuator (one byte for set on/off, one byte for choosing a value between 0 and 255)
    
    - DOUBLE WORLD long actuator (on/off + 3 different values between 0 and 255)

    - Decision maker (as last)
    
For testing will be also needed :

    - GUI visualizer
Both the decision maker and visualizer may either work in polling or with interrupts.
The second way may work if we either use websockets instead of restful services or if use *PUT* http requests (DID NOT CHECK HOW DOES THIS WORK YET).

Upsides of websockets: no need for multiple handshakes, easily keep track of alive microservices and no need for polling at all.

Downsides: Cannot take easy track of weach socket unless a third service is used. With a restful service, opening it's URL is the only step needed to get to see what's going on. => Easier debugging. As a consequence, for this project, the Restful services were chosen. Given how easy the spring framework is, it should not be such an hard work to move to websockets in the future.

RESTful service in spring: https://spring.io/guides/gs/rest-service/

WEBSOCKETS within spring framework: https://spring.io/guides/gs/messaging-stomp-websocket/

Using Maven to compile and handle the services.

For the system deplyment, OSGI would be an option: https://www.theserverside.com/news/1363825/OSGi-for-Beginners ← What is OSGI and how doe sit work


MORE INFOS: 

REST architecture: A rest architecture it's a set of linked resources, which makes a web's subsystem, showed as services. https://restlet.com/use-cases/api-first/rest-apis/

Restlet: REST API, library to create and handle a REST architecture. System of communication between services.

URI: Uniform Resource Identifier, it's the string used to identify and access a resource through internet (like a an URL basically).

Spring MVC: Framework used to build web application using the Model View Controller. Ther first two word mean the input and the output, while the controller computes the inputs and sends the data to the views.
In spring the inputs are model classes to take care of and the views are JSP files (compiled using HTML).
https://www.html.it/pag/44655/spring-mvc-introduzione-2/ (Tutorial in italian).

SPRINGBOOT: it's a configurator for the SPring Framework. It writes the XML files needed without having the programmer to care about them. It basically build a project that is server of himself, which means it does not need a services handler being Apache Tomcat included in the *.jar* itself.
To install SpringBoot within Eclipse: https://www.codejava.net/ides/eclipse/install-spring-tool-suite-for-existing-eclipse-ide
To write a Spring microservices using a maven project within eclipse: https://www.springboottutorial.com/creating-spring-boot-project-with-eclipse-and-maven

THe *annotations* are declaration of the functions to map their functioning and they are compulsory to compile a spring project.

Maven can be seen as an *accumulation of knowledge*. It handles the whole building process, all dependencies, the versions and the documentation of the project. Everything must be set within its configurtation file, the *pom.xml*

How does Maven work it's explained in a basic but really understandable way within spring's tutorials.
###############################################################################################################################
*We care about women and men basically, the other 48 are out of this experiment's scope

UPDATE: the genders are actually 63 now not 50 anymore
