# Progetto-NewVilla
May it be possible making an everyday boring shop compete with amazon's algorithms?

Prove da fare:
- Un servizio REST che risponde ad un get. https://spring.io/guides/gs/rest-service/       (OK)
- Fare un programma che legge da un servizio REST. https://spring.io/guides/gs/consuming-rest/
- Un servizio Rest che accetta un PUT/POST e fa qualcosa a riguardo  https://www.javaguides.net/2019/02/spring-resttemplate-spring-rest-client-get-post-put-delete-example.html
- Un servizietto a cui si collegano gli altri per registrare tutti i servizi sul sistema locale (così basta avviare un servizio perchè esso venga resitrato)
- Trovare modo migliore di condividere dati tra i vari servizi. PEr esempio, la lista di servizi disponibili data dal servizietto precedente,
	come conviene farla? Il servizio salva i disponibili su un file (anche se in tal caso, non vale forse la pena di fare un servizio...
	tanto vale che i servizi scrivano direttamente sul file... o ciò potrebbe causare accavallamenti?) oppure semplicemente interrogano il servizio? (forse 
	meglio l'ultima opzioni a sto punto!).
