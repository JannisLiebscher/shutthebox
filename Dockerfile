FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
RUN apt-get update && apt-get install -y sbt libxrender1 libxtst6 libxi6 
WORKDIR /main.scala.de.htwg.shutthebox
ADD . /main.scala.de.htwg.shutthebox
CMD sbt run
