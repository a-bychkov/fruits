### How to deploy:

- build docker image for agent pod:  
  docker build -t abychkov117/jenkins-agent-pod:latest .
  docker push abychkov117/jenkins-agent-pod:latest


- run jenkins in kubernetes:  
  helm repo add jenkins https://charts.jenkins.io  
  helm repo update jenkins  
  helm install jenkins jenkins/jenkins


- open kubernetes jenkins secret > get login/pass


- port-forward for access jenkins:  
  kubectl port-forward jenkins-0 8080:8080


- create pipeline project  
  provide git repository url  
  specify path for service Jenkinsfile, example: client/.deploy/Jenkinsfile


- provide credentials for Github, DockerHub  
  on Github/DockerHub side create secrets as 'access tokens'  
  on jenkins side create credentials as 'Username with password' kind, with names 'github' & 'dockerhub'