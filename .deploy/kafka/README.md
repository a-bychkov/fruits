### How to deploy:

- create ssl certificates and kubernetes secret > 'create-certs.txt'  


- run in kubernetes:  
  helm repo update    
  helm install -f .\values.yml kafka bitnami/kafka


- check topics:  
  exec kafka-0 pod  
  cd /opt/bitnami/kafka/bin && kafka-topics.sh --list --bootstrap-server kafka-headless:9092


- run cmd consumer:  
  exec kafka-0 pod  
  cd /opt/bitnami/kafka/bin && kafka-console-consumer.sh --bootstrap-server kafka-headless:9092 --topic client_topic --from-beginning