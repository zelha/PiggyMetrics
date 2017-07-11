FROM mongo:latest
MAINTAINER Alexander Lukyanchikov <sqshq@sqshq.com>

ADD init.sh /init.sh
ADD ./dump /

RUN \
 chmod +x /init.sh && \
 apt-get update && \
 apt-get dist-upgrade -y && \
 apt-get install psmisc -y -q && \
 apt-get install dos2unix -y && \
 dos2unix /init.sh && \
 apt-get autoremove -y && apt-get clean && \
 rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["/init.sh"]