package de.htwg.se.stb.model
import scala.util.{Try, Success, Failure}
import de.htwg.se.stb.boardComponent.*
import de.htwg.se.stb.diceComponent.*
import de.htwg.se.stb.playerComponent.*
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import akka.http.scaladsl.unmarshalling.Unmarshal
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import scala.concurrent.ExecutionContextExecutor
import akka.actor.TypedActor.dispatcher
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.Await
import com.typesafe.config.ConfigFactory
import play.api.libs.json.JsNumber
import java.util.concurrent.TimeoutException

object GameClient {
    given system: ActorSystem = ActorSystem("GameService")
    val config = ConfigFactory.load()
    val diceHost = "http://" + config.getString("host.dice") + ":" + config.getString("port.dice")
    val boardHost = "http://" + config.getString("host.board") + ":" +config.getString("port.board")
    val playerHost = "http://" + config.getString("host.player") + ":" +config.getString("port.player")

    def wuerfelnRequest(num: Int): Try[DiceInterface] = {
        val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(uri = diceHost + "/" + config.getString("route.dice.roll") + "/" + num)
        )
        try {
            val response = Await.result(responseFuture, 3.seconds)
            response match {
            case HttpResponse(StatusCodes.OK, _, entity, _) =>
                val entityString =
                Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
                val dice = Dice.fromJson(Json.parse(entityString))
                return Success(dice)
            case _ =>
                return Failure(new Exception("Error fetching Dice Json"))
            }
        } catch {
            case e: TimeoutException => return Failure(new Exception("DiceService nicht erreichbar!"))
        }

    }

    def shutRequest(board: BoardInterface, stone: Int): Try[BoardInterface] = {
        val request = HttpEntity(ContentTypes.`application/json`, Board.toJson(board).toString())
        val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(
            method = HttpMethods.POST,
            uri = boardHost + "/" + config.getString("route.board.shut") + "/" + stone,
            entity = request
        )
        )
        try {
            val response = Await.result(responseFuture, 3.seconds)
            response match {
            case HttpResponse(StatusCodes.OK, _, entity, _) =>
                val entityString =
                Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
                val result = Board.fromJson(Json.parse(entityString))
                Success(result)
            case _ =>
                Failure(new Exception("Error fetching Dice Json"))
            }
        } catch {
            case e: TimeoutException => return Failure(new Exception("BoardService nicht erreichbar!"))
        }
    }

    def resShutRequest(board: BoardInterface, stone: Int): Try[BoardInterface] = {
        val request = HttpEntity(ContentTypes.`application/json`, Board.toJson(board).toString())
        val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(
            method = HttpMethods.POST,
            uri = boardHost + "/" + config.getString("route.board.resshut") + "/" + stone,
            entity = request
        )
        )
        try {
            val response = Await.result(responseFuture, 3.seconds)
            response match {
            case HttpResponse(StatusCodes.OK, _, entity, _) =>
                val entityString =
                Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
                val result = Board.fromJson(Json.parse(entityString))
                Success(result)
            case _ =>
                Failure(new Exception("Error fetching Dice Json"))
            }
        } catch {
            case e: TimeoutException => return Failure(new Exception("BoardService nicht erreichbar!"))
        }
    }


    def endMoveRequest(players: PlayerInterface, board: BoardInterface): Try[PlayerInterface] = {
        val request = HttpEntity(ContentTypes.`application/json`, Players.toJson(players).toString())
        val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(
            method = HttpMethods.POST,
            uri = playerHost + "/" + config.getString("route.player.addscore") + "/" + board.count(),
            entity = request
        )
        )
        try {
            val response = Await.result(responseFuture, 3.seconds)
            response match {
            case HttpResponse(StatusCodes.OK, _, entity, _) =>
                val entityString =
                Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
                val result = Players.fromJson(Json.parse(entityString))
                Success(result)
            case _ =>
                Failure(new Exception("Error fetching Dice Json"))
            }
        } catch {
            case e: TimeoutException => return Failure(new Exception("PlayerService nicht erreichbar!"))
        }
    }
}
    