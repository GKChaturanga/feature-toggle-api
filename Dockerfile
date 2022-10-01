FROM openjdk:17-alpine

RUN mkdir -p /swisscom/

ADD init.sh /swisscom/init/init.sh
RUN chmod 777 /swisscom/init/init.sh


COPY build/libs/*SNAPSHOT.jar /swisscom/boot.jar

EXPOSE 8080
#ENTRYPOINT java -jar /swisscom/boot.jar
ENTRYPOINT  /swisscom/init/init.sh