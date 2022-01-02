# we will use openjdk 8 with alpine as it is a very small linux distro
FROM openjdk:8-jre-alpine3.9

RUN apk add --update \
    curl \
    && rm -rf /var/cache/apk/*

# copy the packaged jar file into our docker image
COPY build/libs/practice-2-all-1.0-SNAPSHOT.jar /app/application.jar

# COPY jmx_prometheus_javaagent-0.13.0.jar /jmx_prometheus_javaagent-0.13.0.jar

COPY prometheus-jmx-config.yaml /prometheus-jmx-config.yaml
COPY src/main/resources/logback.xml /home
COPY newrelic/newrelic.yml /app
COPY newrelic/newrelic.jar /app
COPY newrelic/newrelic-api.jar /app
COPY newrelic/newrelic-api-javadoc.jar /app

WORKDIR /app
# set the startup command to execute the jar
#CMD ["java", "-javaagent:/jmx_prometheus_javaagent-0.13.0.jar=8080:/prometheus-jmx-config.yaml", "-jar","application.jar" ]
#CMD ["java", " -agentlib: -Dlogging.config=/home/logback.xml", "-jar","application.jar" ]


CMD ["java","-javaagent:/app/newrelic.jar", "-jar","application.jar" ]