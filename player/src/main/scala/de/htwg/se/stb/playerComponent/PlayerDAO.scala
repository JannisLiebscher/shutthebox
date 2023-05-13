package de.htwg.se.stb.playerComponent

import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.playerComponent.PlayerTable
import scala.util.Success
import scala.util.Failure

object PlayerDAO {
  val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")

  val playerSchema = TableQuery(new PlayerTable(_))

  def savePlayer(player: PlayerInterface): Future[Int] =  {
    val insertAction = playerSchema += (None, player.getScore(1), player.getScore(2), player.getTurn)
    db.run(insertAction)
  }

  def loadPlayer(id: Int): Future[PlayerInterface] = {
    val query = playerSchema.filter(_.id === id)
    val result = db.run(query.result.headOption)
    result.map {
      case Some((id, player1, player2, turn)) => new Players(2, Vector(("Player 1", player1), 
      ("Player 2", player2)), turn)
      case None => throw new Exception("Player not found")
    }
  }
}
