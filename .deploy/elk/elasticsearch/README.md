### How to deploy:

- build docker image:  
  docker build -t abychkov117/es_image:latest .
  docker push abychkov117/es_image:latest


- run in kubernetes:  
  kubectl apply -f .\elasticsearch-k8s.yml