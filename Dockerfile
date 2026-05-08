# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
# Download dependencies first to leverage Docker cache
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Minimal run environment
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/travel-0.0.1-SNAPSHOT.jar app.jar

# Cloud Run performance optimizations for JVM
ENV JAVA_OPTS="-XX:TieredStopAtLevel=1 -Xverify:none -XX:+UseSerialGC -Xmx512m -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
