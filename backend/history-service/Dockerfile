FROM quay.io/wildfly/wildfly:34.0.1.Final-jdk17
COPY ./build/libs/history-service-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/history-service.war

USER root
RUN mkdir -p /opt/jboss/wildfly/modules/org/postgres/main/
ADD https://jdbc.postgresql.org/download/postgresql-42.7.8.jar /opt/jboss/wildfly/modules/org/postgresql/main/postgresql.jar
COPY config/postgresql-module.xml /opt/jboss/wildfly/modules/org/postgresql/main/module.xml
RUN chown -R jboss:jboss /opt/jboss/wildfly/modules/org

COPY config/standalone.xml /opt/jboss/wildfly/standalone/configuration/standalone.xml
RUN chown jboss:jboss /opt/jboss/wildfly/standalone/configuration/standalone.xml
USER jboss

EXPOSE 8080
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
