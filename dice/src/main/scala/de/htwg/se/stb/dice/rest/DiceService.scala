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

object DiceService {
    val config = ConfigFactory.load()
    val port = config.getInt("port.dice")
    private var server: Option[Http.ServerBinding] = None
    given system: ActorSystem = ActorSystem("DiceService")
    @main def main = {
      var w: DiceInterface = Dice("two")
      val route = path("wuerfeln" / IntNumber) {
        num =>
        get {
          w = w.wuerfeln(num)
          println("Served Dice: " + w.toString())
          val json = Dice.toJson(w)
          complete(json.toString())
        }
      } ~
      path("wuerfel") {
        get {
          val json = Dice.toJson(w)
          complete(json.toString())
        }
      } ~
      path("shutdown") {
      get {
        shutdown()
        complete("Server shutting down...")
        }
      }
      val server = Some(Http().newServerAt("localhost", port).bind(route))
      server.get.map { _ => 
        println("Server online at http://localhost:" + port)
      }  recover { case ex => 
        println(s"Server could not start: ${ex.getMessage}")
      }
    }
    def shutdown(): Unit = {
    println("Server shutting down...")
    system.terminate()
  }
}
