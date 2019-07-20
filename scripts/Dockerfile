FROM tomcat
MAINTAINER scottejames@gmail.com
ENV DM_DEV false
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]

COPY ./target/downsman-1.13-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]

