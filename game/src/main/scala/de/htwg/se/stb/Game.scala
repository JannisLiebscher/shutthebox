package de.htwg.se.stb.model
import scala.util.{Try, Success, Failure}
import fileioComponent.FileIOXML
import de.htwg.se.stb.model.fileioComponent.FileIOJSON
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
val eol = sys.props("line.separator")
given system: ActorSystem = ActorSystem("GameService")
val config = ConfigFactory.load()
val diceHost = "http://" + config.getString("host.dice") + ":" + config.getString("port.dice")
val boardHost = "http://" + config.getString("host.board") + ":" +config.getString("port.board")
val playerHost = "http://" + config.getString("host.player") + ":" +config.getString("port.player")

case class Game(
    board: BoardInterface,
    dice: DiceInterface,
    players: PlayerInterface,
    sum: Int
) extends GameInterface {
  def this() = this(new Board(), Dice("two"), new Players(2), 0)
  def _getBoard: BoardInterface = board
  def _getDice: DiceInterface = dice
  def _getPlayers: PlayerInterface = players
  def count(): Int = board.count()
  def getDice: String = dice.toString
  def getSum: Int = sum
  def getPlayers: String = players.toString
  def getWinner: Option[String] = players.getWinner
  def getTurn: Int = players.getTurn
  def getBoard: String = board.toString
  def isShut(stone: Int): Boolean = board.isShut(stone)
  def getScore(player: Int): Int = players.getScore(player)
  def wuerfeln(num: Int): Try[Game] = {
    var result: Try[Game] = Failure(
      new Exception("complete turn before rolling dice again")
    )
    if (sum == 0) {
      val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(uri = diceHost + "/" + config.getString("route.dice.roll") + "/" + num)
      )
      val response = Await.result(responseFuture, 3.seconds)
      response match {
        case HttpResponse(StatusCodes.OK, _, entity, _) =>
          val entityString =
            Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
          val tmp = Dice.fromJson(Json.parse(entityString))
          result = Success(new Game(board, tmp, players, tmp.getSum()))
        case _ =>
          result = Failure(new Exception("Error fetching Dice Json"))
      }
    }
    result
  }

  def shut(stone: Int): Try[Game] =
    if (board.isShut(stone)) Failure(new Exception("already shut"))
    else if (board.count() < sum)
      Failure(new Exception(
          "Impossible to use whole Sum, let the next player take their turn"))
    else if (stone > sum)
      Failure(new Exception("Cant shut box bigger than whole sum"))
    else {
      val request = HttpEntity(ContentTypes.`application/json`, Board.toJson(board).toString())
      val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(
          method = HttpMethods.POST,
          uri = boardHost + "/" + config.getString("route.board.shut") + "/" + stone,
          entity = request
        )
      )
      val response = Await.result(responseFuture, 3.seconds)
      response match {
        case HttpResponse(StatusCodes.OK, _, entity, _) =>
          val entityString =
            Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
          val tmp = Board.fromJson(Json.parse(entityString))
          Success(new Game(tmp, dice, players, sum - stone))
        case _ =>
          Failure(new Exception("Error fetching Dice Json"))
      }
    }
    

  def resShut(stone: Int): Try[Game] =
    if (board.isShut(stone))
      val request = HttpEntity(ContentTypes.`application/json`, Board.toJson(board).toString())
      val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(
          method = HttpMethods.POST,
          uri = boardHost + "/" + config.getString("route.board.resshut") + "/" + stone,
          entity = request
        )
      )
      val response = Await.result(responseFuture, 3.seconds)
      response match {
        case HttpResponse(StatusCodes.OK, _, entity, _) =>
          val entityString =
            Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
          val tmp = Board.fromJson(Json.parse(entityString))
          Success(new Game(tmp, dice, players, sum + stone))
        case _ =>
          Failure(new Exception("Error fetching Dice Json"))
      }
    else Failure(new Exception("already shut"))

  def endMove: Try[Game] =
    if (dice.getSum() != sum && sum != 0) Failure(new Exception("shut all boxes"))
    else {
      val request = HttpEntity(ContentTypes.`application/json`, Players.toJson(players).toString())
      val responseFuture: Future[HttpResponse] = Http().singleRequest(
        HttpRequest(
          method = HttpMethods.POST,
          uri = playerHost + "/" + config.getString("route.player.addscore") + "/" + board.count(),
          entity = request
        )
      )
      val response = Await.result(responseFuture, 3.seconds)
      response match {
        case HttpResponse(StatusCodes.OK, _, entity, _) =>
          val entityString =
            Await.result(entity.toStrict(3.seconds), 3.seconds).data.utf8String
          val tmp = Players.fromJson(Json.parse(entityString))
          Success(new Game(new Board(9), Dice("two"), tmp, 0))
        case _ =>
          Failure(new Exception("Error fetching Dice Json"))
      }
    }
      

  override def toString(): String =
    if(players.getWinner.isDefined) players.toString
    else players.toString + eol +
      board.toString + eol +
      "Gewuerfelt " + dice.toString + " | Summe: " + sum
}

object Game:
  def apply(): Game = new Game(new Board(), Dice("two"), new Players(2), 0)
  def apply(kind: String) = kind match {
    case "mock" | "Mock" =>
      new Game(new Board(), Dice("mock"), new Players(2), 0)
  }
  def toJson(game: GameInterface) = Json.obj(
      "board" -> Board.toJson(game._getBoard),
      "players" -> Players.toJson(game._getPlayers),
      "dice" -> Dice.toJson(game._getDice),
      "sum" -> JsNumber(game.getSum)
    )
  def fromJson(json: JsValue):GameInterface = 
    val dice = Dice.fromJson((json \ "dice").get)
    val board = Board.fromJson((json \ "board").get)
    val players = Players.fromJson((json \ "players").get)
    val sum = (json \ "sum").get.toString().toInt
    new Game(board, dice, players, sum)
