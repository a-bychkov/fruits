FROM docker.elastic.co/elasticsearch/elasticsearch:7.10.1

# copy the configuration file into the container
COPY elasticsearch.yml /usr/share/elasticsearch/config

EXPOSE 9200/tcp

# run elastic with non root user
USER elasticsearch