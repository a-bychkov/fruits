FROM openjdk:17-alpine

#Refer to builded jar file
ARG JAR_FILE=target/*.jar

#cd /opt/app
WORKDIR /opt/app

#cp target/client-<ver>.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

#java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]