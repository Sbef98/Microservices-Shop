TIPI DI MICROSERVIZI DA PROVARE AD IMPOSTARE:
    - Microservizi di stato (ora, data)
    - Microservizio meteo
    - Microservizio numero persone (IOLO)
    - Attuatore(acceso/spento)
    - Attuatore(acceso/spento, byte)
    - attuatore(acceso/spento, 3 byte)

    - Decisore (e profiler non più necessario) per ultimi
Inoltre sartà necessario, a soli fini illustrativi:
    - Visualizzatore
Quest'ultimo può o funzionare in polling o, sarebbe meglio, ad interrupt. La maniera più semplice per utilizzarlo a mo di
interrupt (e sarebbe anche la maniera più efficiente ad ogni modo) è attrverso websockets (vedasi 
https://spring.io/guides/gs/messaging-stomp-websocket/), o altrimenti VERIFICARE SE CON LE RICHIESTE HTTP DI PUT anzichè
di get possa funzionare ugualmente (usando rest).
Sia nel caso si utilizzino i sockets o l'architettura rest si utilizzerà il framework spring(boot) data la sua semplicità
ed immediatezza. RESTful service in spring: https://spring.io/guides/gs/rest-service/

Per compilare/gestire il progetto utilizzeremo maven.

Il passo successivo sarebbe utilizzare, per eseguire il system deplyment dei servizi, OSGI:
https://www.theserverside.com/news/1363825/OSGi-for-Beginners ← cos’è osgi e come funziona


INFORMAZIONI AGGIUNTIVE
REST architecture: 
un’architettura REST è un set di risorse collegate, che formano un sottoinsieme del web, mostrati come microservizi.
https://restlet.com/use-cases/api-first/rest-apis/

Restlet: REST API, libreria per creare e gestire un’architettura REST, sistema di comunicazione tra i microservizi;
URI: Uniform Resource Identifier, la stringa per identificare una risorsa e accedervi via internet (in parole povere un URL).
Spring MVC: Framework(libreria) per creare applicazione web secondo un modello Model View Controller, 
i primi due termini rappresentano input e output, il controller elabora gli input e passa i dati alle view. 
In spring gli input sono le classi modello da gestire e le view sono file JSP(Compilati in HTML)
https://www.html.it/pag/44655/spring-mvc-introduzione-2/

Springboot: E’ un configuratore dello Spring framework, crea gli XML necessari senza che sia tu a scriverli.
Crea un progetto che “è server di sé stesso”, non ha bisogno di un gestore di servizi, il “.jar” prodotto include 
nativamente Apache Tomcat (o altri gestori) che avviano il tuo servizio. Serve a non dover gestire il deploy dei vari servizi.
Per installare springboot su eclipse:
https://www.codejava.net/ides/eclipse/install-spring-tool-suite-for-existing-eclipse-ide
Per scrivere un microservizio springboot con Maven:
https://www.springboottutorial.com/creating-spring-boot-project-with-eclipse-and-maven
Le annotations sono dichiarazioni delle funzioni per mappare il loro funzionamento e sono obbligatorie per compilare.
Maven: “Accumulation of knowledge”, gestisce le builds, le dependencies + versions e la documentation di un progetto, 
gira tutto intorno ad un file principale di configurazione: “pom.xml”. 
COme utilizzare maven è spiegato in maniera basilare ma molto efficace nei vari tutorial di Spring

