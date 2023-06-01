package de.htwg.se.stb.model.persistence

import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.diceComponent.DiceTable
import de.htwg.se.stb.playerComponent.PlayerTable
import de.htwg.se.stb.boardComponent.BoardTable

class GameTable(tag: Tag) extends Table[(Option[Int], Int, Int, Int, Int)](tag, "game") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def diceId = column[Int]("dice_id")
  def boardId = column[Int]("board_id")
  def playerId = column[Int]("player_id")
  def totalSum = column[Int]("total_sum")

  
  
  val diceSchema = TableQuery(new DiceTable(_))
  val playerSchema = TableQuery(new PlayerTable(_))
  val boardSchema = TableQuery(new BoardTable(_))
    
  def dice = foreignKey("dice_fk", diceId, diceSchema)(_.id)
  def board = foreignKey("board_fk", boardId, boardSchema)(_.id)
  def player = foreignKey("player_fk", playerId, playerSchema)(_.id)

  def * = (id.?, diceId, boardId, playerId, totalSum)
}
