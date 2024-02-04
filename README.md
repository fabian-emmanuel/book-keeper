# Book-keeper API
The Book-keeper API is a robust REST service built with Java and the Spring Boot framework, 
designed to manage a simple online bookstore.

#### TECHNOLOGY USED
- **Java**: Programming language
- **Maven**: Build tool and project management
- **SpringBoot (Reactive/WebFlux)**: Framework for building Java-based enterprise applications
- **Postgres**: Relational database for data storage
- **Swagger (SpringDoc)**: API documentation tool
- **Docker**: Containerization platform
- **Faker**: Tool for generating fake data for testing purposes


#### DESIGN EXPLANATION
The Book-keeper API is monolithic application, designed to follow RESTful principles, 
with each endpoint representing a specific resource and supporting standard CRUD operations. 
The application utilizes a reactive programming model with Spring Boot's WebFlux framework for improved scalability.
All endpoints (aside the user registration endpoint) are protected, 
hence users are required to register either as an admin or a user.
Only users with admin role can register/update books, all  other users can retrieve books.

#### PROJECT REQUIREMENT

To Run The Application, Ensure You Have The Following:

- [Docker](https://docs.docker.com/engine/install/)


## Getting Started

#### Clone the Project:
You can clone the project using the following link:

```bash
git clone https://github.com/fabian-emmanuel/book-keeper.git
```
#### Build And Run The Application
Navigate to the project's root directory and execute the following commands:

```bash
./mvnw clean install     # Build the project
docker-compose build     # Build Docker image
docker-compose up        # Run the project using Docker
```
#### Run The Tests
```bash
./mvnw test              # Run the tests
```

#### Accessing The Documentations
- [Swagger Documentation](http://localhost:9000/api/v1/webjars/swagger-ui/index.html)
- [Postman Documentation](https://documenter.getpostman.com/view/32364986/2s9Yywde6Z)


### NOTES
`Application Runs On Port: 9000, with base-path: api/v1, Hence all endpoints are accessed via http://localhost:9000/api/v1/**`

Detailed endpoint documentation is available on the SwaggerUI and Postman Doc [Links Provided Above].
After running the application, you can explore the documentation and test the endpoints accordingly.
