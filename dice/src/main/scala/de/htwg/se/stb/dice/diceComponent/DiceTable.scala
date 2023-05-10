package de.htwg.se.stb.diceComponent

import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._

class DiceTable(tag: Tag) extends Table[(Int, Int, Int)](tag, "dice") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def w1 = column[Int]("w1")
  def w2 = column[Int]("w2")
  
  def * = (id, w1, w2)
}
