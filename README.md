# SwisCom feature toggle
## Demo Application 
SwisCom feature toggle is a jvm based RESTfull web service powered by Spring Boot  and ReactJS UI 
By Kasun Chaturanga Gajamange / kchaturanga@gmail.com

---

## Installation

- Checkout the both UI and API in to a same workspace 
- Open **.env**  file and  Change _LOG_PATH_ and _DATASTORE_PATH host paths 
- Add your preferred port to **.env** *APPLICATION_PORT*
- Make sure you have java 17 installed ( Or higher )
- Execute *build.sh* and it will 
		- compile application 
		- Execute all integration tests 
		- Invoke docker compose build 
		- Invoke docker compose up 
		
---

- docker-compose will bootup  Application with  
		- java 17 based container for application created with .Dockerfile
		- feturetoggle-ui With ReactJS on a node JS user 
		- MySQL 8 container 


## Application Architecture 
- Spring Boot 2.7.1  / Spring 5x  / ReactJS on UI
- Containerized deployment support     

## Simple Data Model

![alt Starbux Class Diagram ](StarbuxClassDiagram.jpg)

## Default Data 

Default data initialization is managed by **flyaway**db migrations  
for production check 
 - V1__create_table.sql
 - V2__create_default_data.sql
 `

## Testing 
Integration tests are created for all  main use cases 
- mysql Test Profile is initiated with embedded 
		- *h2* db for relational data with different test  data set 
*/feature-toggle-api/src/main/resources/data-h2.sql*


## ReactJS UI 
ReactJS UI created based on  https://minimal-kit-react.vercel.app/ 

## Highlights 
- Project is created with Spring Starter / Eclipse IDE 2022-06 / ReactJS UI
- Default profile for production and test profile for testing 
- MySQL 8 / flyaway db / h2 / hibernate / jpa / spring data / data store on mounted host volume
- slf4j logging on mounted host volume , RollingFileAppender  / logback-spring.xml
- Spring AOP for logging and error handling .
- Each end point  compliant with the HTTP/1.1 and REST standards .
- RestController / Mappings  with OpenAPIDefinitions 
- Use of spring-boot-starter-validation for validate request body on POST and PATCH  
- Global Error handling through ControllerAdvice
- Use of Streams , Builder pattern / Facade /  lambda functions / Functional Interfaces 
- Docker / Docker Compose  / Gradle and shell scripts  
 
## Improvements  
**Non Functional**
- Integrate Database service / cluster for MySQL instead of local mounted container 
- Create proper logging and log monitoring process using filebeat and ELK stack 
- Create application monitoring service / end points / health 
- Using ontainer orchestration tool or a framework like AWS ecs or Kubernetes 

 
