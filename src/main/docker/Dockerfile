FROM centos

ENV CENARIOVR_VER 1.0.0

# install java
RUN yum install -y java

VOLUME /tmp
EXPOSE 8080
ADD /gbm-challenge-0.0.1-SNAPSHOT.jar cenariovr.jar

RUN sh -c 'touch /cenariovr.jar'

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/cenariovr.jar"]

#docker build -t iris .
#docker run -d -p 8080:8080 iris
#docker logs [id]
