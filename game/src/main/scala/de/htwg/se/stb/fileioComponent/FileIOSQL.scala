package de.htwg.se.stb.model.fileioComponent
import de.htwg.se.stb.model.fileioComponent.FileIOInterface
import de.htwg.se.stb.model.*
import scala.concurrent.Await
import scala.concurrent.duration._

object FileIOSQL extends FileIOInterface {
  var saved = 1
  override def delete: Unit = ???
  override def update(game: GameInterface): Unit = ???
  override def load: GameInterface = {
    Await.result(GameDAOSQL.loadGame(saved), 3.seconds)
  }

  override def save(game: GameInterface) = {
   saved = Await.result(GameDAOSQL.saveGame(game), 3.seconds)
  }
  
}
