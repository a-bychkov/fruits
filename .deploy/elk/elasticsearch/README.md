### How to deploy:

- build docker image:  
  docker build -t abychkov117/es_image:latest .  
  docker push abychkov117/es_image:latest

- increase vm for docker:  
  copy '.wslconfig' file to 'C:\Users\<your_user>\' directory  
  restart docker

- local run:  
  docker run -d --name elastic -p 9200:9200 abychkov117/es_image:latest

- run in kubernetes:  
  kubectl apply -f .\elasticsearch-k8s.yml