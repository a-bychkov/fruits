### How to deploy:

- build docker image:  
  mvn clean package  
  docker build -t abychkov117/client-app:latest .
  docker push abychkov117/client-app:latest


- run in kubernetes:  
  cd .\.deploy && kubectl apply -f .\client-app-k8s.yml