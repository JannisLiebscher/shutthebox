package de.htwg.se.stb.boardComponent

import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.boardComponent.BoardTable
import scala.util.Success
import scala.util.Failure

object BoardDAO {
  val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")
  val boardSchema = TableQuery(new BoardTable(_))

  def saveBoard(board: BoardInterface): Future[Int] =  {
    val insertAction = boardSchema += (None, toBinary(board))
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
