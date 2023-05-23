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

object GameDAOSQL  extends  GameDAO {
  val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")
  val gameSchema = TableQuery(new GameTable(_))
  db.run(gameSchema.schema.create)
  override def deleteGame: Unit = ???
  override def updateGame(game: GameInterface): Unit = ???
  def saveGame(game: GameInterface): Future[Int] = {
    val diceId =  Await.result(DiceDAOSQL.saveDice(game._getDice), 3.seconds)
    val boardId = Await.result(BoardDAOSQL.saveBoard(game._getBoard), 3.seconds)
    val playerId = Await.result(PlayerDAOSQL.savePlayer(game._getPlayers), 3.seconds)
    val insertAction = gameSchema returning gameSchema.map(_.id)
      += (None, diceId, boardId, playerId, game.getSum)
    db.run(insertAction)
  }


  def loadGame(id: Int): Future[GameInterface] =  {
   val query = gameSchema.filter(_.id === id)
    val result = db.run(query.result.headOption)
    result.map {
      case Some((id, diceId, boardId, playerId, sum)) => {
        val dice =  Await.result(DiceDAOSQL.loadDice(diceId), 3.seconds)
        val board = Await.result(BoardDAOSQL.loadBoard(boardId), 3.seconds)
        val player = Await.result(PlayerDAOSQL.loadPlayer(playerId), 3.seconds)
        new Game(board, dice, player, sum)
      }
      case None => throw new Exception("Game not found")
    }
  }
}
