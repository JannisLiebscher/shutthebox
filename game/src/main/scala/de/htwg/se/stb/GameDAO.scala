package de.htwg.se.stb.model

import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.model.GameTable
import de.htwg.se.stb.model.GameInterface
import scala.util.Success
import scala.util.Failure
import de.htwg.se.stb.playerComponent.PlayerDAO
import de.htwg.se.stb.boardComponent.BoardDAO
import de.htwg.se.stb.model.Game
import de.htwg.se.stb.diceComponent._
import scala.concurrent.Await
import scala.concurrent.duration._

object GameDAO {
  val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")
  val gameSchema = TableQuery(new GameTable(_))
  def create() = {
    DiceDAO.create()
    BoardDAO.create()
    PlayerDAO.create()
    db.run(gameSchema.schema.create).onComplete {
      case Success(_) => println("Created Tables")
      case Failure(exception) => println(s"Failed to create tables: ${exception.getMessage}")
    }
  }
  def saveGame(game: GameInterface): Future[Int] = {
    val diceId =  Await.result(DiceDAO.saveDice(game._getDice), 3.seconds)
    val boardId = Await.result(BoardDAO.saveBoard(game._getBoard), 3.seconds)
    val playerId = Await.result(PlayerDAO.savePlayer(game._getPlayers), 3.seconds)
    val insertAction = gameSchema returning gameSchema.map(_.id)
      += (None, diceId, boardId, playerId, game.getSum)
    db.run(insertAction)
  }


  def loadGame(id: Int): Future[GameInterface] =  {
   val query = gameSchema.filter(_.id === id)
    val result = db.run(query.result.headOption)
    result.map {
      case Some((id, diceId, boardId, playerId, sum)) => {
        val dice =  Await.result(DiceDAO.loadDice(diceId), 3.seconds)
        val board = Await.result(BoardDAO.loadBoard(boardId), 3.seconds)
        val player = Await.result(PlayerDAO.loadPlayer(playerId), 3.seconds)
        new Game(board, dice, player, sum)
      }
      case None => throw new Exception("Game not found")
    }
  }
}
