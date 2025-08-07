FROM gradle:jdk21-ubi-minimal AS build

WORKDIR /app

# Copy necessary files and folder from project for building a jar file
COPY gradle ./gradle
COPY src ./src
COPY settings.gradle .
COPY build.gradle .

# run gradle command to make a jar file
RUN gradle bootJar

# ──────────────────────────────
# 2) Runtime Stage
# ──────────────────────────────
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the generated .jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]