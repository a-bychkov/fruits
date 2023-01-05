### How to deploy:

- build docker image:  
  docker build -t abychkov117/logstash_image:latest .  
  docker push abychkov117/logstash_image:latest


- run in kubernetes:  
  kubectl apply -f .\logstash-k8s.yml