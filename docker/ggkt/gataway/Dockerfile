FROM java:8
MAINTAINER songml
VOLUME /ggkt
ADD service_gateway-0.0.1-SNAPSHOT.jar ggkt_gateway.jar
EXPOSE 8333
ENTRYPOINT ["java","-jar","ggkt_gateway.jar"]