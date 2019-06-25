# Exchange Rates Service

This is the source code for the service that gets exchange rates from external sources and aggregates them in database

## Currently supported providers:
* European Central Bank
* National Bank of Poland

## Technology used for it:
* Spring Boot
* Kotlin
* MongoDB
* Docker

# Getting started

You need Java, maven and MongoDB installed.

    mvn install
    cd docker && docker-compose up --build
    
After that you can open `http://localhost:8180/swagger-ui.html` to start playing with service

## Additional information
There are two config files. One to run service locally and second in 'docker/config' folder for running in Docker.
Make sure that there will be no missing entries in docker or you will get errors.

Logs with level WARN or higher are stored in `logs/service-warn.log` file in JSON format ready for Logstash
Configuration of Log4j is in `src/main/resources/log4j2.xml`

## TODO
* Tests
* More providers
* Add script in docket that will wait till db will be accessible then run service

# Help

Please fork and PR to improve the code.

# Kotlin

I've been using Kotlin for some time, but I'm no expert, so feel free to contribute and modify the code to make it more idiomatic!

