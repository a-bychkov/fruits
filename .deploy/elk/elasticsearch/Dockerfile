FROM openjdk:8-jdk-alpine

#create user
RUN adduser esuser -u 1000 -h /home/esuser -D

WORKDIR /home/esuser

#directory with elasticsearch data - for volume mount
RUN mkdir /data

#update libraries for install
RUN apk update

#download and install elasticsearch
RUN \
	wget https://download.elasticsearch.org/elasticsearch/release/org/elasticsearch/distribution/tar/elasticsearch/2.1.1/elasticsearch-2.1.1.tar.gz && \
	tar xvzf elasticsearch-2.1.1.tar.gz && \
	rm -f elasticsearch-2.1.1.tar.gz && \
	chown -R esuser:esuser elasticsearch-2.1.1

#copy config to container
ADD elasticsearch.yml /home/esuser/elasticsearch-2.1.1/config/elasticsearch.yml

EXPOSE 9200/tcp

ENTRYPOINT elasticsearch-2.1.1/bin/elasticsearch