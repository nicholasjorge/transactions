# Transactions demo application

## Installing Tools
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

docker compose up -> to run the container inside Docker

#### Running the applications
- Download the zip or clone the Git repository.
- Unzip the zip file (if you downloaded one)
- Open Command Prompt and Change directory (cd) to folder containing pom.xml/Import the projects into the IDE in order to launch them from there.
- Before launching the applications make sure the rabbitMQ container/server is running. PS: the app is configured to run with the default ports for rabbitMQ !!!
- IMPORTAT!! Run the applicatins as spring boot app, they must be run in a specific order:
		- 1)	eureka-server
		- 2),3)	persitence-service/transaction-service, doesn't really matter, the only thing is that they must register to the service discovery server.
		- 4)	api-gateway

#### The applications can be found at the following ports:(existing configuration)
- eureka-server port: 8761(default)
- persitence-service port: 8000
- transaction-service port: 8100
- api-gateway port: 8765

They can be changed based on your environment using command line arguments -Dserver.port=XXXX, with the desired port.

#### Endpoints to access the resources exposed by the applications:
Microservice 1 (transaction-service) -> The main endpoints for the microservice are:
- localhost:8100/api/transactions, GET-> returns all the transactions
- localhost:8100/api/transactions/report, GET-> returns the report for each user of the application, with it's transactions.
- localhost:8100/transactions, POST with the content in this JSON structure: 
{
"transactionType": "IBAN_TO_WALLET",
"name": "George",
"cnp": "1936605562840",
"iban": "RO34232SDGWD3234324",
"description": "transfer",
"amount": 500
}

Hystrix is used in case of failure of the peristence-service, having fallback methods for each request, returning no content, until the service is back on.
While the service is down, all the incoming transactions to be created are stored in the queue in RabbitMQ, and will be persisted when the peristence-service will be back on.
In order to see the messages in the rabbitMQ you need to enable the console and login with : guest/guest. The channel is configured as transactions, and the group is transactions-group.(configured properties)
Ribbon is used to load balance the available services for persistence, based on the application name in eureka.

Microservice 2 (persitence-service): Spring Data REST exposes rest endpoints based on the respository, so the endpoints for the persitence-service will be /transactions, with HATEOAS support to make the service self-described.
The data is persisted into a H2 in-memory db, which can be accessed usign the /h2-console endpoint.

The second way to access the persistence-service enpoints is through the Zuul Proxy, which routes the calls from outside, through the service discovery server, based on the application names: example -> localhost:8100/persistence-service/transactions, this is what is called an "edge service".

Lastly, using spring-cloud-sleuth, i enabled log tracing through distributed microservices, in order to trace the log informations between microservices.

#### MENTIONS: 
I did not implemented a centralized server for logging, it could be Zipkin or ELK stack in order to see the whole flow of the message/requests and the duration of the process.
I did not implemented also the config server for centralized configuration, using the git repo for my configuration, used to refresh the properties without build and deployment, just using the actuator /refresh endpoint.
Also missing from the applications is the security part, which can be implement easly in the gateway, with in-memory/database/oauth2 security.
Also i don't have a front-end for the application for better look of the extracted data.
