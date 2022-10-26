### How to deploy:

- build docker image:  
  mvn clean package  
  docker build -t abychkov117/client-app:latest .
  docker push abychkov117/client-app:latest


- database instance for local run:  
  docker run -d --name mysql -e MYSQL_DATABASE=mydb -e MYSQL_USER=user -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 mysql:latest


- run in kubernetes:  
  cd .\.deploy && kubectl apply -f .\client-app-k8s.yml