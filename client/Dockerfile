FROM openjdk:17-alpine

#Refer to builded jar file
ARG JAR_FILE=target/*.jar

#cd /opt/apps
WORKDIR /opt/apps

#cp target/client-<ver>.jar /opt/apps/app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080/tcp

#java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=cluster","app.jar"]