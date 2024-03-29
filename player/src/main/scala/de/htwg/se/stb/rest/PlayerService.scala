package de.htwg.se.stb.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global
import de.htwg.se.stb.playerComponent.*
import play.api.libs.json._
import com.typesafe.config.ConfigFactory
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration._

object PlayerService {
    val config = ConfigFactory.load()
    val port = config.getInt("port.player")
    private var server: Option[Http.ServerBinding] = None
    given system: ActorSystem = ActorSystem("PlayerService")
    @main def main = {
      var players: PlayerInterface = new Players(2)
      val route = path(config.getString("route.player.players")) {
        get {
          val json = Players.toJson(players)
          complete(json.toString())
        }
      } ~
      path(config.getString("route.player.addscore") / IntNumber) {
        num =>
        post {
          entity(as[String]) { body =>
            val players = Players.fromJson(Json.parse(body))
            val json = Players.toJson(players.addScore(num))
            complete(json.toString())
          }
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