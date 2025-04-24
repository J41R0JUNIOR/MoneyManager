# Step 1: Build with Maven
FROM maven:3.9-amazoncorretto-17 AS builder

WORKDIR /app

# Copy to the image
COPY . /app

# Compile the project and generate the .jar
RUN mvn clean package -DskipTests

# Step 2: Final image .jar
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

# Copy the .jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
