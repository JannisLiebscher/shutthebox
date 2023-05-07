FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
# Curl and Nano
RUN apt-get update && apt-get install -y curl nano
# App
WORKDIR /shutthebox
ADD ./dice /shutthebox/dice
ADD ./board /shutthebox/board
ADD ./player /shutthebox/player
ADD ./game /shutthebox/game
ADD ./src /shutthebox/src
ADD ./build.sbt /shutthebox/build.sbt
ADD ./project/plugins.sbt /shutthebox/project/plugins.sbt
# Start command
CMD sbt compile && tail -f /dev/null






