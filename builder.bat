::Nos movemos al Frontend
SET mypath=%~dp0

SET newFile=%mypath%ultragamecenter\src\main\resources\static\new\

echo %newFile%

cd ./Frontend
cmd /C ng build --prod --base-href="/new/"
cd ../

DEL /Q %newFile%

mkdir %newFile%
::Construimos la aplicacion de angular y lo movemos al proyecto spring
cd ./Frontend/dist/angular-ugc
copy * %newFile%

::Contruimos el dockerfile
cd ../../../ultragamecenter

cmd /C docker run --rm -v "%cd%":/data -w /data maven mvn package
cmd /C docker build -t rodri666a/webapp9 -f docker/Dockerfile .

::Pusheamos
docker push rodri666a/webapp9

cd docker

docker-compose up --build
