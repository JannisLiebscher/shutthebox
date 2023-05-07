FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
# Curl and Nano
RUN apt-get update && apt-get install -y curl nano
# App
WORKDIR /board
ADD ./board /board
ADD ./src/main/resources/application.conf ./src/main/resources/application.conf
ADD ./build.sbt.tpl ./build.sbt
# Start command
CMD sbt run





