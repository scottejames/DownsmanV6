FROM tomcat
MAINTAINER scottejames@gmail.com
ENV DM_DEV false
RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]

COPY ./target/downsman-1.13-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
COPY ./resources/server.xml /usr/local/tomcat/conf/server.xml

CMD ["catalina.sh", "run"]

