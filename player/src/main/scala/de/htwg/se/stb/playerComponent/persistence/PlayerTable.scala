package de.htwg.se.stb.playerComponent.persistence

import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._

class PlayerTable(tag: Tag) extends Table[(Option[Int], Int, Int, Int)](tag, "player") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def player1 = column[Int]("player1")
  def player2 = column[Int]("player2")
  def turn = column[Int]("turn")
  
  def * = (id.?, player1, player2, turn)
}
