FROM openjdk:11
MAINTAINER scottejames@gmail.com
ENV DM_DEV false

RUN mkdir /usr/src/myapp
COPY ./target/downsman-4.0-SNAPSHOT.jar /usr/src/myapp
WORKDIR /usr/src/myapp

CMD ["java", "-jar", "downsman-4.0-SNAPSHOT.jar"]

