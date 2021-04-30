::Nos movemos al Frontend
cd ./Frontend
cmd /C ng build --prod --base-href="/new/"

::Construimos la aplicacion de angular y lo movemos al proyecto spring
cd ./dist/angular-ugc

copy * "../../../ultragamecenter/src/main/resources/static/new/"

::Contruimos el dockerfile
cd ../../../ultragamecenter

cmd /C docker run --rm -v "%cd%":/data -w /data maven mvn package
cmd /C docker build -t rodri666a/webapp9 -f docker/Dockerfile .

::Pusheamos
docker push rodri666a/webapp9

cd docker

docker-compose up --build
