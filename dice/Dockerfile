FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
# Curl and Nano
RUN apt-get update && apt-get install -y curl nano
# App
WORKDIR /dice
ADD . /dice
RUN mkdir src/main/resources
RUN mv application.conf src/main/resources/application.conf
# Start command
CMD sbt run





