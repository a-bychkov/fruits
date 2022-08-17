FROM openjdk:8-jdk-alpine

#create user
RUN adduser esuser -u 1000 -h /home/esuser -D

WORKDIR /home/esuser

RUN mkdir /data

#update libraries for install
RUN apk update && \
	apk add --update nodejs shadow bash

#download and install kibana
RUN \
	wget https://download.elastic.co/kibana/kibana/kibana-4.3.1-linux-x64.tar.gz && \
	tar xvzf kibana-4.3.1-linux-x64.tar.gz && \
	rm -f kibana-4.3.1-linux-x64.tar.gz && \
	chown -R esuser:esuser kibana-4.3.1-linux-x64

#copy config to container
ADD kibana.yml kibana-4.3.1-linux-x64/config/kibana.yml

#fix node installation (https://github.com/elastic/kibana/issues/17015)
ENV KIBANA_DIR=/home/esuser/kibana-4.3.1-linux-x64

RUN rm -rf ${KIBANA_DIR}/node && \
	mkdir -p ${KIBANA_DIR}/node/bin && \
	ln -s /usr/bin/node ${KIBANA_DIR}/node/bin/node
  
EXPOSE 5601/tcp
  
ENTRYPOINT kibana-4.3.1-linux-x64/bin/kibana