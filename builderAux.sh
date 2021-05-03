# Nos movemos al Frontend
mypath=${pwd}

newFile=${mypath}\ultragamecenter\src\main\resources\static\new\


cd ./Frontend
ng build --prod --base-href=/new/
cd ../

rm $newFile

mkdir $newFile
# Construimos la aplicacion de angular y lo movemos al proyecto spring
cd ./Frontend/dist/angular-ugc
cp * $newFile
