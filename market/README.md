### How to deploy:

- build docker image:  
  mvn clean package  
  docker build -t abychkov117/market-app:latest .
  docker push abychkov117/market-app:latest


- database instance for local run:  
  docker run -d --name mysql -e MYSQL_DATABASE=mydb -e MYSQL_USER=user -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 mysql:latest
- kafka for local run:  
  cd ..\.deploy\kafka\local && docker-compose --project-name=kafka-local up


- run in kubernetes:  
  cd .\.deploy && kubectl apply -f .\market-app-k8s.yml