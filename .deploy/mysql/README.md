### How to deploy:

- database instance for local run:  
  docker run -d --name mysql -e MYSQL_DATABASE=mydb -e MYSQL_USER=user -e MYSQL_PASSWORD=password -e MYSQL_ROOT_PASSWORD=password -p 3306:3306 mysql:latest


- run in kubernetes:  
helm repo update bitnami  
helm install -f .\values.yml mysql bitnami/mysql