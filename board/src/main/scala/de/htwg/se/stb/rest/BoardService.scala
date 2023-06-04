package de.htwg.se.stb.rest

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global
import de.htwg.se.stb.boardComponent.*
import play.api.libs.json._
import com.typesafe.config.ConfigFactory
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.Await
import scala.concurrent.duration._

object BoardService {
    val config = ConfigFactory.load()
    val port = config.getInt("port.board")
    private var server: Option[Http.ServerBinding] = None
    given system: ActorSystem = ActorSystem("BoardService")
    @main def main = {
      val board = new Board().shut(1).shut(3).shut(6)
      val route = path(config.getString("route.board.shut") / IntNumber) {
        num =>
        post {
          entity(as[String]) { body =>
            val board = Board.fromJson(Json.parse(body))
            val json = Board.toJson(board.shut(num))
            complete(json.toString())
          }
        }
      } ~
      path(config.getString("route.board.resshut") / IntNumber) {
        num =>
        post {
          entity(as[String]) { body =>
            val board = Board.fromJson(Json.parse(body))
            val json = Board.toJson(board.resShut(num))
            complete(json.toString())
          }
        }
      } ~
      path(config.getString("route.board.isshut") / IntNumber) {
        num =>
        get {
          println("test")
          val json = Json.obj("isShut" -> JsBoolean(board.isShut(num)))
          complete(json.toString())
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

