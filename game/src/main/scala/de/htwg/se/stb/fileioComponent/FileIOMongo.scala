package de.htwg.se.stb.model.fileioComponent
import de.htwg.se.stb.model.fileioComponent.FileIOInterface
import de.htwg.se.stb.model.*
import scala.concurrent.Await
import scala.concurrent.duration._

class FileIOMongo extends FileIOInterface {
  var saved = -1
  override def load: GameInterface = {
    Await.result(GameDAOMongo.loadGame(saved), 3.seconds)
  }

  override def save(game: GameInterface) = {
   saved = Await.result(GameDAOMongo.saveGame(game), 3.seconds)
  }
  
}
