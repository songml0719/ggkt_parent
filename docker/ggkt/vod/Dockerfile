FROM java:8
MAINTAINER songml
VOLUME /ggkt
ADD docker/ggkt/vod/service_vod-0.0.1-SNAPSHOT.jar ggkt_vod.jar
ENTRYPOINT ["java","-jar","ggkt_vod.jar"]