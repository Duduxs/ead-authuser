<h4 align="center">
  <p>Ead Authuser</p>
  
  <p>Business Project of the ead's microservice group</p>
  
  <p>It was created in order to manages users and all microservices authorization by jwt tokens.</p>
  
</h4>

<p align="center">
  <a href="#rocket-technologies">Technologies</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#information_source-how-to-use">How To Use</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#thumbsup-how-to-contribute">How To Contribute</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#memo-license">License</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#handshake-ead-ecosystem">Ead Ecosystem</a>
</p>

<p align="center">
<img alt="Collage" src="https://ik.imagekit.io/27ewoxssse/authuser_ydtY269MC.png?ik-sdk-version=javascript-1.4.3&updatedAt=1651488086047"> 
</p>

## :rocket: Technologies

This project was developed with the following technologies:

- API:

  - [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  - [JJWT](https://github.com/jwtk/jjwt)
  - [Specification Arg Resolver](https://github.com/tkaczmarzyk/specification-arg-resolver)
  - [Lombok](https://projectlombok.org/)
  - [MapStruct](https://mapstruct.org/)
  - [Postgres](https://www.postgresql.org/)
  - [Spring Web](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html)
  - [Spring JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
  - [Spring Validation](https://docs.spring.io/spring-framework/docs/4.1.x/spring-framework-reference/html/validation.html)
  - [Spring Hateoas](https://docs.spring.io/spring-hateoas/docs/current/reference/html/#reference)
  - [Spring AMQP](https://spring.io/projects/spring-amqp)
  - [Spring Security](https://spring.io/projects/spring-security)
  - [Spring Circuit Breaker](https://spring.io/projects/spring-cloud-circuitbreaker)
  - [Spring Actuator](https://docs.spring.io/spring-boot/docs/2.5.6/reference/html/actuator.html)
  - [Spring Cloud](https://spring.io/projects/spring-cloud)
  - [Spring Cloud Config Server](https://spring.io/projects/spring-cloud-config#overview)
  - [Spring Eureka Client](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html)

  
- Utils:

  - [Spring Log4j2](https://docs.spring.io/spring-boot/docs/2.1.18.RELEASE/reference/html/boot-features-logging.html)
  - [Spring DevTools](https://docs.spring.io/spring-boot/docs/1.5.16.RELEASE/reference/html/using-boot-devtools.html)
  - [Docker](https://www.docker.com/)
  - [Docker Compose](https://docs.docker.com/compose/)
  
- Tests:

  - [H2](https://h2database.com/html/main.html)


## :information_source: How to use
To clone and run these configurations, you'll need [Git](https://git-scm.com), From your command line:

```bash
# Clone this repository
$ git clone https://github.com/Duduxs/ead-authuser

# Now clone the service-registry repo
$ git clone https://github.com/Duduxs/ead-service-registry

# And config-server repos
$ git clone https://github.com/Duduxs/ead-config-server
$ git clone https://github.com/Duduxs/ead-config-server-repo

```

To run the authuser:

```bash
# Execute the main class AuthuserApplication with dev profile (Be sure to have already service registry and config server up) 
```

## :thumbsup: How To Contribute

-  Make a fork;
-  Create a branch with your feature: `git checkout -b my-feature`;
-  Commit changes: `git commit -m 'feat: My new feature'`;
-  Make a push to your branch: `git push origin my-feature`.

## :memo: License
This project is under the MIT license. See the [LICENSE](https://github.com/Duduxs/ead-authuser/blob/main/LICENSE) for more information.


---

## :handshake: Ead Ecosystem
Take a look into others microservices that were developed for the EAD Plataform.
 
 - Cross Cuting Microservices
    - <a href="https://github.com/Duduxs/ead-service-registry">Service Registry</a>
    - <a href="https://github.com/Duduxs/ead-config-server-repo">Config Server</a>
    - <a href="https://github.com/Duduxs/ead-config-server">Config Server (Impl)</a>
    - <a href="https://github.com/Duduxs/ead-api-gateway">Gateway</a>
- Business Microservices
    - <a href="https://github.com/Duduxs/ead-authuser">Auth User</a>
    - <a href="https://github.com/Duduxs/ead-course">Course</a>
    - <a href="https://github.com/Duduxs/ead-notification">Notification</a>
- Study case Microservices
    - <a href="https://github.com/Duduxs/ead-notification-hex">Notification With Hexagonal Architecture</a>
    
---

<h4 align="center">
    Made by Eduardo José 😆 <a href="https://www.linkedin.com/in/eduarddojose/" target="_blank">Contact me!</a>
</h4>
