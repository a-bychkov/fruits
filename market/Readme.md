### How to deploy:

- build docker image:  
  mvn clean package  
  docker build -t abychkov117/market-app:latest .
  docker push abychkov117/market-app:latest


- run in kubernetes:  
  cd .\.deploy && kubectl apply -f .\market-app-k8s.yml