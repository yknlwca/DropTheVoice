FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

# Step 2: Create the final image with JRE and the built application
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/a505-0.0.1-SNAPSHOT.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
CMD ["java","-Dspring.profiles.active=prod,local", "-jar", "app.jar"]