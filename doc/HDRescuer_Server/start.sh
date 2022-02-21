#! /bin/sh

(cd api_gateway_module/ && docker-compose up -d)
(cd api_composer_module/ && docker-compose up -d)
(cd users_module/ && docker-compose up -d)
(cd session_module/ && docker-compose up -d)
(cd data_recovery_module/ && docker-compose up -d)


#Matar los contenedores con docker kill $(docker ps -q) o ejecutar el sh stop.sh
#Eliminar todos los contenedores docker rm $(docker ps -a -q)
#Eliminar todas las imágenes docker rmi $(docker images -q)
