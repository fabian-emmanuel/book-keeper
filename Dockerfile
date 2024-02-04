FROM eclipse-temurin:21-alpine

VOLUME /tmp

COPY target/book-keeper-1.0.0.jar book-keeper.jar

ENTRYPOINT ["java", "-Dspring.r2dbc.url=r2dbc:postgresql://postgres:5432/book-keeper-db", "-jar", "book-keeper.jar"]
