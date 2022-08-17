FROM openjdk:8-jdk-alpine

#create user
RUN adduser esuser -u 1000 -h /home/esuser -D

WORKDIR /home/esuser

RUN mkdir /data

#update libraries for install
RUN apk update && \
	apk add --no-cache bash

#download and install logstash
RUN \
	wget https://download.elastic.co/logstash/logstash/logstash-2.1.1.tar.gz && \
	tar xvzf logstash-2.1.1.tar.gz && \
	rm -f logstash-2.1.1.tar.gz && \
	chown -R esuser:esuser logstash-2.1.1

#copy config to container
ADD logstash.conf /home/esuser

EXPOSE 5044/tcp
EXPOSE 5000/tcp

CMD logstash-2.1.1/bin/logstash -f logstash.conf --verbose