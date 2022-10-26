### How to deploy:

enable services external access in kubernetes:  
- kubectl apply -f .\ingress.yml
- minikube addons enable ingress
- minikube tunnel

testing connection > https://localhost/client/actuator