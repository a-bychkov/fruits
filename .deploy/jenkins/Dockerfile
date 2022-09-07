#Jenkins kubernetes agent pod
FROM openjdk:17-alpine

USER root

#update repo and install curl
RUN apk update && apk upgrade && apk add curl

#install maven
RUN apk add maven

#install docker
RUN apk add docker

#install kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \
    install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

#copy kubeconfig for kubectl work
COPY /kubeconfig /var/config
ENV KUBECONFIG=/var/config