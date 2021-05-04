cd ..

docker run --rm -v "%cd%":/data -w /data maven mvn package & docker build -t rodri666a/webapp9 -f docker/Dockerfile .


