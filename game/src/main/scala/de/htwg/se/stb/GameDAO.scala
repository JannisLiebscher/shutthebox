package de.htwg.se.stb.model

import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.model.GameTable
import de.htwg.se.stb.model.GameInterface
import scala.util.Success
import scala.util.Failure
import de.htwg.se.stb.playerComponent.PlayerDAOSQL
import de.htwg.se.stb.boardComponent.BoardDAOSQL
import de.htwg.se.stb.model.Game
import de.htwg.se.stb.diceComponent._
import scala.concurrent.Await
import scala.concurrent.duration._

trait GameDAO {
  
  def saveGame(game: GameInterface): Future[Int]
  def loadGame(id: Int): Future[GameInterface]
}
