FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY target/exchangeRates-*.jar $PROJECT_HOME/service.jar
COPY docker/config/application.yml  $PROJECT_HOME/application.yml

WORKDIR $PROJECT_HOME

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./service.jar"]
