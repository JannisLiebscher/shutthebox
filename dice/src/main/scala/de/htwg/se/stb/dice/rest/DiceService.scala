package de.htwg.se.stb.dice.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global
import de.htwg.se.stb.diceComponent.Dice
import de.htwg.se.stb.diceComponent.TwoDice
//import spray.json.DefaultJsonProtocol._


object DiceService {
    def main = {
      given system: ActorSystem = ActorSystem("DiceService")
      val sum = 2
      val route = path("test") {
        get {
          complete("OK")
        }
      }
      val server = Http().newServerAt("localhost", 8080).bind(route)
      server.map { _ => 
        println("Server online at http://localhost:8080/")
      }  recover { case ex => 
        println(s"Server could not start: ${ex.getMessage}")
      }
    }
}
