# Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# We use -Dmaven.compiler.release=17 here because this image definitely supports it
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# This picks up the JAR regardless of the name
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Use shell form so ${PORT} is expanded at runtime by the shell
CMD ["sh", "-c", "java -Dserver.port=${PORT:-8080} -Dserver.address=0.0.0.0 -jar app.jar"]