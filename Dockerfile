FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/messages-mngr-1.0.0.jar

WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]