package de.htwg.se.stb.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global
import de.htwg.se.stb.diceComponent.*
import play.api.libs.json._
import com.typesafe.config.ConfigFactory
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.diceComponent.DiceDAO
import scala.concurrent.Await
import scala.concurrent.duration._

object DiceService {
    val config = ConfigFactory.load()
    val port = config.getInt("port.dice")
    private var server: Option[Http.ServerBinding] = None
    given system: ActorSystem = ActorSystem("DiceService")
    @main def main = {

    val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")
    val diceSchema = TableQuery(new DiceTable(_))
    db.run(diceSchema.schema.create)
    val dice = new TwoDice
    val insertAction = diceSchema += (None, dice.toString().head.asDigit, dice.toString().last.asDigit)
    db.run(insertAction)


      var w: DiceInterface = Dice("two")
      val route = path(config.getString("route.dice.roll") / IntNumber) {
        num =>
        get {
          w = w.wuerfeln(num)
          val json = Dice.toJson(w)
          complete(json.toString())
        }
      } ~
      path("save") {
        get {
          val newDice = new TwoDice
          val d = Await.result(DiceDAO.loadDice(1), 3.seconds)
          println(d)
          complete("OK")
        }
      } ~
      path(config.getString("route.shutdown")) {
      get {
        shutdown()
        complete("Server shutting down...")
        }
      } ~
      path(config.getString("route.check")) {
      get { complete("OK") }
      }
      val server = Some(Http().newServerAt("0.0.0.0", port).bind(route))
      server.get.map { _ => 
        println("Server online at http://" + config.getString("host.dice") + ":" + port)
      }  recover { case ex => 
        println(s"Server could not start: ${ex.getMessage}")
      }
    }
    def shutdown(): Unit = {
    println("Server shutting down...")
    system.terminate()
  }
}