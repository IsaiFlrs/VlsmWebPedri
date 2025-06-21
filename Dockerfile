FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:9.0.85-jdk17-corretto
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app/target/CalculadoraVLSM-1.0.war /usr/local/tomcat/webapps/ROOT.war

ENV CATALINA_OPTS="-Dserver.servlet.context-path=/"
EXPOSE 8080
CMD ["catalina.sh", "run"]
