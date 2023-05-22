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

object GameDAOMongo {
  val database_pw = "password"
  val database_username = "root"
  val uri: String = s"mongodb://$database_username:$database_pw@localhost:27017/?authSource=admin"
  val mongoClient: MongoClient = MongoClient(uri)
  val database: MongoDatabase = mongoClient.getDatabase("shutthebox")
  val gameCollection: MongoCollection[Document] = database.getCollection("game")
  val ping = database.runCommand(Document("ping" -> 1)).head()
  Await.result(ping, 3.seconds)
  def saveGame(game: GameInterface): Future[Int] = {
    val gameDoc = Document(
      "test" -> 1,
      "string"  -> "hi")
    println(gameDoc)
    gameCollection.insertOne(gameDoc)
    Future.successful(1)
  }

  def toInt(objectId: ObjectId): Int = {
    val byteArray = objectId.toByteArray
    val firstFourBytes = byteArray.take(4)
    val intValue = java.nio.ByteBuffer.wrap(firstFourBytes).getInt
    intValue
  }
  def loadGame(id: Int): Future[GameInterface] = ???
  
  
}
