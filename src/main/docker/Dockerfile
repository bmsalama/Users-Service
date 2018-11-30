FROM anapsix/alpine-java:8
#FROM inventx/redhat-openjdk-18
VOLUME /tmp
ARG JARFILE=./@project.build.finalName@.jar
COPY $JARFILE /app.jar
RUN bash -c 'touch /app.jar'
#ENTRYPOINT java $JAVA_OPTIONS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
ENTRYPOINT java -Xmx600m -Xms600m -XX:+UseParallelGC -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=40 -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -XX:MaxMetaspaceSize=100m -XX:+ExitOnOutOfMemoryError -Duser.timezone=Asia/Riyadh -Djava.security.egd=file:/dev/./urandom  -jar /app.jar
