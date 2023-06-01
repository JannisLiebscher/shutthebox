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
import com.mongodb.client.result.DeleteResult
import org.mongodb.scala.model.Updates.set
import scala.util.{Try, Success, Failure}
import org.bson.BsonDocument

object GameDAOMongo extends GameDAO{
  val database_pw = "password"
  val database_username = "root"
  val uri: String = "mongodb://root:password@localhost:27017/?authSource=admin"
  val client: MongoClient = MongoClient(uri)
  val db: MongoDatabase = client.getDatabase("shutthebox")
  val gameCollection: MongoCollection[Document] = db.getCollection("game")
  var id: Int = 0

  override def deleteGame = 
   gameCollection.deleteMany(equal("_id", id)).subscribe(
      (dr: DeleteResult) => println(s"Deleted gameDocument"),
      (e: Throwable) => println(s"Error while trying to delete gameDocument: $e")
    )
    Future {
      id -= 1
      "Finished deleting!"
    }
  override def updateGame(game: GameInterface) = 
    observerUpdate(gameCollection.updateOne(equal("_id", id), 
      set("game", BsonDocument.parse(Game.toJson(game).toString()))))

  def saveGame(game: GameInterface): Future[Int] = {
    id = getMaxId() + 1
    println("Saving Game " + id)
    var gameDoc = Document("_id" -> id, "game" -> BsonDocument.parse(Game.toJson(game).toString()))
    observerInsertion(gameCollection.insertOne(gameDoc))
    Future.successful(id.toString().toInt)
  }
  def loadGame(id: Int): Future[GameInterface] = {
    val gameDocument: Document = Await.result(gameCollection.find(equal("_id", id)).first().head(), 3.seconds)
    val json: JsValue = Json.parse(gameDocument("game").asDocument().toJson())
    val dice = Dice.fromJson((json \ "dice").get)
    val board = Board.fromJson((json \ "board").get)
    val players = Players.fromJson((json \ "players").get)
    val sum = (json \ "sum").get.toString().toInt
    Future.successful(new Game(board, dice, players, sum))
  }
  private def getMaxId(): Int = {
  val doc: Document = Await.result(gameCollection.find().sort(descending("_id")).first().head(), 3.seconds)
  Try(doc("_id").asInt32().getValue()) match {
    case Success(value) => value
    case Failure(_) => 0
  }
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
