FROM eclipse-temurin:25-jre-jammy

RUN useradd -ms /bin/bash appuser
USER appuser

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]