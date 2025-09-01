FROM eclipse-temurin:21-jre-alpine
LABEL maintainer=Иван
WORKDIR /app
COPY libs libs/
COPY resources resources/
COPY classes classes/
ENTRYPOINT ["java", "-Dspring.profiles.active=production", "-Xmx2048m", "-cp", "/app/resources:/app/classes:/app/libs/*", "org.example.sql_tutorial.SqlTutorialApplication"]
EXPOSE 8080
