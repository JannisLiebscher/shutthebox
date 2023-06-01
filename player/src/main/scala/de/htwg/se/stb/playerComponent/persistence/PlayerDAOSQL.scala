package de.htwg.se.stb.playerComponent.persistence

import de.htwg.se.stb.playerComponent.*
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import concurrent.ExecutionContext.Implicits.global
import com.typesafe.config.ConfigFactory

object PlayerDAOSQL extends PlayerDAO{
  val config = ConfigFactory.load()
  val url = config.getString("mariadb.url")
  val user = config.getString("mariadb.user")
  val pw = config.getString("mariadb.password")
  val db = Database.forURL(url, user, pw, driver = "org.mariadb.jdbc.Driver")

  val playerSchema = TableQuery(new PlayerTable(_))
  db.run(playerSchema.schema.create)
  def savePlayer(player: PlayerInterface): Future[Int] =  {
    val insertAction = playerSchema returning playerSchema.map(_.id)
      += (None, player.getScore(1), player.getScore(2), player.getTurn)
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
