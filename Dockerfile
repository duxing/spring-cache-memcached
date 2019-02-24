FROM gradle:jdk8-alpine as builder
USER root
ENV GRADLE_OPTS -Dorg.gradle.daemon=false
WORKDIR /app
COPY . .

RUN gradle -q build

FROM openjdk:8-jre-alpine

RUN apk --no-cache add bash curl && \
  mkdir -p /var/log/application

WORKDIR /opt/app
COPY --from=builder /app/build/libs/spring-cache-memcached.jar ./application.jar
COPY ./entrypoint.sh .

EXPOSE 8080
ENTRYPOINT ./entrypoint.sh
