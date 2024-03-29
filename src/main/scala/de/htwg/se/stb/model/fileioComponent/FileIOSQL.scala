package de.htwg.se.stb.model.fileioComponent
import de.htwg.se.stb.model.persistence.GameDAOSQL
import de.htwg.se.stb.model.*
import scala.concurrent.Await
import scala.concurrent.duration._

object FileIOSQL extends FileIOInterface {
  var saved = 1
  override def delete: Unit = None
  override def update(game: GameInterface) = save(game)
  override def load: GameInterface = {
    Await.result(GameDAOSQL.loadGame(saved), 3.seconds)
  }

  override def save(game: GameInterface) = {
   saved = Await.result(GameDAOSQL.saveGame(game), 3.seconds)
  }
  
}
