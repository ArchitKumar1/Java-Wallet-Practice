# we will use openjdk 8 with alpine as it is a very small linux distro
FROM openjdk:8-jre-alpine3.9

# copy the packaged jar file into our docker image
COPY build/libs/practice-2-all-1.0-SNAPSHOT.jar /application.jar
COPY jmx_prometheus_javaagent-0.13.0.jar /jmx_prometheus_javaagent-0.13.0.jar

COPY prometheus-jmx-config.yaml /prometheus-jmx-config.yaml

# set the startup command to execute the jar
CMD ["java", "-javaagent:/jmx_prometheus_javaagent-0.13.0.jar=8080:/prometheus-jmx-config.yaml", "-jar","application.jar" ]