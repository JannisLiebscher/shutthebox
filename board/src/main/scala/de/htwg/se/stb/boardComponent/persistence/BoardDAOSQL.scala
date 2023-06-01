package de.htwg.se.stb.boardComponent.persistence

import de.htwg.se.stb.boardComponent.*
import de.htwg.se.stb.boardComponent.persistence.BoardTable
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import concurrent.ExecutionContext.Implicits.global
import com.typesafe.config.ConfigFactory

object BoardDAOSQL extends BoardDAO {
  val config = ConfigFactory.load()
  val url = config.getString("mariadb.url")
  val user = config.getString("mariadb.user")
  val pw = config.getString("mariadb.password")
  val db = Database.forURL(url, user, pw, driver = "org.mariadb.jdbc.Driver")
  val boardSchema = TableQuery(new BoardTable(_))
  db.run(boardSchema.schema.create)
  
  def saveBoard(board: BoardInterface): Future[Int] =  {
    val insertAction = boardSchema returning boardSchema.map(_.id) 
      += (None, toBinary(board))
    db.run(insertAction)
  }
  def toBinary(board: BoardInterface  ): Int = {
    val shutList = (1 to 9).map(n => if (board.isShut(n)) 1 else 0)
    Integer.parseInt(shutList.mkString(""), 2)
  }

  def loadBoard(id: Int): Future[BoardInterface] = {
    val query = boardSchema.filter(_.id === id)
    val result = db.run(query.result.headOption)
    result.map {
      case Some((id, fields)) => fromBinary(fields)
      case None => throw new Exception("Board not found")
    }
  }
  def fromBinary(fields: Int): BoardInterface = {
    val boardstate = fields.toBinaryString.split("").takeRight(9).reverse.padTo(9, "0").reverse
    return (0 to 8)
    .filter(i => boardstate(i).equals("1"))
    .foldLeft(new Board(): BoardInterface)((acc, i) => acc.shut(i + 1))
  }

}
