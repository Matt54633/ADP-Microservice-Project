FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]