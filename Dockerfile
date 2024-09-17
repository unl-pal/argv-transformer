FROM alpine:3.20.3

# Enable community repository
RUN sed -i '2s/^# *//' /etc/apk/repositories
RUN apk update
RUN apk add bash
RUN apk add git
RUN apk add apache-ant
# RUN apk add gradle
RUN apk add openjdk8

# Build Java Path Finder
RUN mkdir /usr/lib/JPF
WORKDIR /usr/lib/JPF
RUN git clone https://github.com/yannicnoller/jpf-core
RUN git clone https://github.com/SymbolicPathFinder/jpf-symbc

# Setup jpf env
RUN mkdir /root/.jpf
RUN echo 'jpf-core = /usr/lib/JPF/jpf-core' >> /root/.jpf/site.properties
RUN echo 'jpf-symbc = /usr/lib/JPF/jpf-symbc' >> /root/.jpf/site.properties
RUN echo 'extensions=${jpf-core},${jpf-symbc}' >> /root/.jpf/site.properties

# Build JPF and JPF-symbc
WORKDIR /usr/lib/JPF/jpf-core
RUN ant build

WORKDIR /usr/lib/JPF/jpf-symbc
RUN ant build

# Setup path
ENV JPF_HOME=/usr/lib/JPF
ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk/
ENV LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JPF_HOME/jpf-symbc/lib

# Work Area
VOLUME /transformer
WORKDIR /transformer
CMD ["/bin/bash"]
