FROM openjdk:17-alpine

USER root

WORKDIR /opt/zipkin

#update repo and install extra soft
RUN apk update && apk upgrade && apk add curl openssl bash --no-cache

#download zipkin-server-2.12.9
RUN curl -o ./zipkin-server.jar "https://repo1.maven.org/maven2/io/zipkin/java/zipkin-server/2.12.9/zipkin-server-2.12.9-exec.jar"

EXPOSE 9411/tcp

#run zipkin server
ENTRYPOINT ["java","-jar","zipkin-server.jar"]