FROM openjdk:8-jre

MAINTAINER stt <stt@qq.com>

ENV APP_VERSION 1.0.0-SNAPSHOT

ENV DOCKERIZE_VERSION v0.6.1

RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN mkdir /app

COPY itoken-eureka-$APP_VERSION.jar /app/app.jar

# egd表示文件上传时使用的安全参数
# 查看分布式配置中心是否启动完成
ENTRYPOINT ["dockerize", "-timeout", "5m", "-wait", "http://192.168.119.132:8888/itoken-eureka/master", "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]

EXPOSE 8761