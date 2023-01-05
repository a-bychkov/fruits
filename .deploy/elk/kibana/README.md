### How to deploy:

- build docker image:  
  docker build -t abychkov117/kibana_image:latest .  
  docker push abychkov117/kibana_image:latest


- run in kubernetes:  
  kubectl apply -f .\kibana-k8s.yml