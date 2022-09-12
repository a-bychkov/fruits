#Jenkins kubernetes agent pod
FROM openjdk:17-alpine

USER root

#update repo and install extra soft
RUN apk update && apk upgrade && apk add curl openssl bash --no-cache

#install maven
RUN apk add maven

#install docker
RUN apk add docker

#install kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \
    install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

#install helm
RUN curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 && \
chmod 700 get_helm.sh && ./get_helm.sh

#add bitnami helm repo
RUN helm repo add bitnami https://raw.githubusercontent.com/bitnami/charts/index/bitnami

#copy kubeconfig for kubectl/helm work
COPY /kubeconfig /var/config
ENV KUBECONFIG=/var/config