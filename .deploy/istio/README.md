### How to deploy:

enable services external access in kubernetes:  
- install istio first
- kubectl apply -f .\ingress-gateway.yml
- kubectl apply -f .\virtualservice.yml
- minikube addons enable ingress
- minikube tunnel

testing connection > localhost/market/actuator