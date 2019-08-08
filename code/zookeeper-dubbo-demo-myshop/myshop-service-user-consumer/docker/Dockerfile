FROM openjdk:8-jre
MAINTAINER Lusifer <topsale@vip.qq.com>

ENV APP_VERSION 1.0.0-SNAPSHOT
ENV DOCKERIZE_VERSION v0.6.1

RUN mkdir /app

COPY dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz /app/dockerize.jar.gz

RUN tar -C /usr/local/bin -xzvf /app/dockerize.jar.gz

COPY myshop-service-user-consumer-$APP_VERSION.jar /app/app.jar

ENTRYPOINT ["dockerize", "-timeout", "5m", "-wait", "tcp://192.168.119.132:20881", "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

EXPOSE 8601 8701