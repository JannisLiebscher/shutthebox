package de.htwg.se.stb.model.fileioComponent
import de.htwg.se.stb.model.fileioComponent.FileIOInterface
import de.htwg.se.stb.model.*
import scala.concurrent.Await
import scala.concurrent.duration._

class FileIOSQL extends FileIOInterface {
  var saved = 1
  override def load: GameInterface = {
    val result = Await.result(GameDAO.loadGame(saved), 3.seconds)
    println("Loaded Game" + saved)
    result
  }

  override def save(game: GameInterface) = {
   saved = Await.result(GameDAO.saveGame(game), 3.seconds)
   println("Saved Game " + saved)
  }
}
