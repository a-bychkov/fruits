### How to deploy:

- build docker image:  
  docker build -t abychkov117/zipkin:latest .  
  docker push abychkov117/zipkin:latest


- run in kubernetes:  
  kubectl apply -f .\zipkin-k8s.yml