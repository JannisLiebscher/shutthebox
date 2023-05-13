package de.htwg.se.stb.gameComponent

import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.model.GameTable
import de.htwg.se.stb.model.GameInterface
import scala.util.Success
import scala.util.Failure
import de.htwg.se.stb.diceComponent.DiceDAO
import de.htwg.se.stb.playerComponent.PlayerDAO
import de.htwg.se.stb.boardComponent.BoardDAO
import de.htwg.se.stb.model.Game
import de.htwg.se.stb.diceComponent.TwoDice

object GameDAO {
  val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")
  val gameSchema = TableQuery(new GameTable(_))
  def saveGame(game: GameInterface): Future[Int] = {
    val diceId = de.htwg.se.stb.diceComponent.Dice
    return Future(3)
  }


  def loadGame(id: Int): Future[GameInterface] =  {
   return Future(new Game())
  }
}
