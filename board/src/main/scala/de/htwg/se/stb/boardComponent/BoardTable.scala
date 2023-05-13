package de.htwg.se.stb.boardComponent

import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._

class BoardTable(tag: Tag) extends Table[(Option[Int], Int)](tag, "board") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def fields = column[Int]("fields")
  
  def * = (id.?, fields)
}
