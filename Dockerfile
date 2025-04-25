FROM eclipse-temurin:17-alpine
WORKDIR /app
COPY target/ticketeira-1.0.jar ticketeira-1.0.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/ticketeira-1.0.jar "]