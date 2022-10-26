### How to deploy:

- create ssl certificates and kubernetes secret > 'create-certs.txt'  


- run in kubernetes:  
  helm repo update    
  helm install -f values.yml kafka bitnami/kafka