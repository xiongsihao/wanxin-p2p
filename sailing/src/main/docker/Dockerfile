FROM openjdk:8-jre-alpine

ENV VERSION 1.0-SNAPSHOT

RUN echo "http://mirrors.aliyun.com/alpine/v3.8/main" > /etc/apk/repositories \
    && echo "http://mirrors.aliyun.com/alpine/v3.8/community" >> /etc/apk/repositories \
    && apk update upgrade \
    && apk add --no-cache procps curl bash tzdata \
    && ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone

ADD sailing-${VERSION}.jar sailing.jar
RUN bash -c 'touch /sailing.jar'

EXPOSE 56085

ENV JAVA_OPTS=""
ENTRYPOINT ["bash", "-c", "java $JAVA_OPTS -jar /sailing.jar"]
