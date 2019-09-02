# Transactions demo application
//TODO update
## Installing Tools
- Java 8
- Eclipse/IDEA/STS/NetBeans/etc
- Maven
- Postman/Other rest client
- Git Client - https://git-scm.com/
- Rabbit MQ - https://www.rabbitmq.com/download.html

### Installing Rabbit MQ

#### Windows
- https://www.rabbitmq.com/install-windows.html
- https://www.rabbitmq.com/which-erlang.html
- http://www.erlang.org/downloads
- Video - https://www.youtube.com/watch?v=UnKbvqVKB7k

#### Mac
- https://www.rabbitmq.com/install-homebrew.html

ADVICE: The easiest way to have access to RabbitMQ is to have Docker installed and start a container based on the image rabbitmq, there is a docker-compose.yml file in the root directory.

`docker-compose up` -> to run the container inside Docker

#### Running the applications
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml/Import the projects into the IDE in order to launch them from there.
- Before launching the applications make sure the rabbitMQ container/server is running. PS: the app is configured to run with the default ports for rabbitMQ !!!
- IMPORTAT!! Run the applicatins as spring boot app, they must be run in a specific order:
    1)	eureka-server
    2)  persitence-service
    3)  transaction-service, doesn't really matter, the only thing is that they must register to the service discovery server.
    4)	api-gateway (OPTIONAL)

#### The applications can be found at the following ports(current configuration):
- eureka-server port: 8761(default)
- persitence-service port: 8000
- transaction-service port: 8100
- api-gateway port: 8765

You can change the application.yml files for other specific ports or based on your environment using command line arguments -Dserver.port=XXXX on starting, with the desired port.

Also, Swagger2 UI is available for both microservices, at the following endpoint: /swagger-ui.html

####Microservice 1 (transaction-service)
- POST localhost:8100/api/transactions, with the content in this JSON structure to create a transaction:

```` json
{
"transactionType": "IBAN_TO_WALLET",
"name": "George",
"cnp": "1936605562840",
"iban": "RO34232SDGWD3234324",
"description": "transfer",
"amount": 500.99
} 
````

Validation:
- transactionType: only those 4 allowed types, not empty
- name: max 255 chars, not empty or null
- cnp: exactly 13 chars, numeric, not empty or null
- iban: max 34 alphanumeric chars, not empty or null
- description: max 255 chars, not required
- amount: max 9 integers, with 2 decimals, positive numbers only
 
BeanValidation is used for input validation, and other properties besides the required ones are ignored by Jackson.

####Microservice 2 (persistence-service): 
- GET locahost:8000/persistence/report -> returns the required report for the transactions: grouped by IBAN, counting the transactions, and grouping them based on the type and cnp of the client, summing the amount per client, per type.

The data is persisted into a H2 in-memory db, which can be accessed usign the /h2-console endpoint. (JDBC URL: jdbc:h2:mem:testdb)

##### Accessing the services via the api-gateway
- localhost:8765/transaction-service/api/transactions, POST with the above structure
- localhost:8765/persistence-service/persistence/report, GET

While the service is down, all the incoming transactions to be created are stored in the queue in RabbitMQ, and will be persisted when the peristence-service will be back on.(Spring-cloud-stream)
In order to see the messages in the rabbitMQ you need to enable the console and login with : guest/guest. The channel is configured as transactions, and the group is transactions-group.(configured properties)

#### MENTIONS: 
I did not implemented a centralized server for logging, it could be Zipkin or ELK stack in order to see the whole flow of the message/requests and the duration of the process.
I did not implemented also the config server for centralized configuration, using the git repo for my configuration, used to refresh the properties without build and deployment, just using the actuator /refresh endpoint.
Also missing from the applications is the security part, which can be implement easly in the gateway, with in-memory/database/oauth2 security.
Also i don't have a front-end for the application for better look of the extracted data.
