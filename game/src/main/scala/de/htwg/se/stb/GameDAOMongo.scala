package de.htwg.se.stb.model

import scala.concurrent.Future
import org.mongodb.scala._
import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.bson.ObjectId
import concurrent.ExecutionContext.Implicits.global
import de.htwg.se.stb.model.fileioComponent.FileIOJSON
import play.api.libs.json.Json
import scala.util.Using
import scala.concurrent.Await
import scala.concurrent.duration._
import com.mongodb.client.result.UpdateResult
import com.mongodb.client.result.InsertOneResult
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import play.api.libs.json.JsValue
import de.htwg.se.stb.diceComponent.Dice
import de.htwg.se.stb.boardComponent.Board
import de.htwg.se.stb.playerComponent.Players

object GameDAOMongo {
  val database_pw = "password"
  val database_username = "root"
  val uri: String = "mongodb://root:password@localhost:27017/?authSource=admin"
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("shutthebox")
  val gameCollection: MongoCollection[Document] = db.getCollection("game")
  var id: Int = 0

  def saveGame(game: GameInterface): Future[Int] = {
    id += 1
    var gameDoc = Document("_id" -> id, "game" -> FileIOJSON()._gameToJson(game).toString())
    observerInsertion(gameCollection.insertOne(gameDoc))
    Future.successful(id.toString().toInt)
  }
  def loadGame(id: Int): Future[GameInterface] = {
    val gameDocument: Document = Await.result(gameCollection.find(equal("_id", id)).first().head(), Duration.Inf)
    val json: JsValue = Json.parse(gameDocument("game").asString().getValue().toString())
    val dice = Dice.fromJson((json \ "dice").get)
    val board = Board.fromJson((json \ "board").get)
    val players = Players.fromJson((json \ "players").get)
    val sum = (json \ "sum").get.toString().toInt
    Future.successful(new Game(board, dice, players, sum))
  }

  private def observerInsertion(insertObservable: SingleObservable[InsertOneResult]): Unit = {
    insertObservable.subscribe(new Observer[InsertOneResult] {
      override def onNext(result: InsertOneResult): Unit = println(s"inserted: $result")

      override def onError(e: Throwable): Unit = println(s"insert onError: $e")

      override def onComplete(): Unit = println("completed insert")
    })
  }

  private def observerUpdate(insertObservable: SingleObservable[UpdateResult]): Unit = {
    insertObservable.subscribe(new Observer[UpdateResult] {
      override def onNext(result: UpdateResult): Unit = println(s"updated: $result")

      override def onError(e: Throwable): Unit = println(s"update onError: $e")

      override def onComplete(): Unit = println("completed update")
    })
  }
  
}
