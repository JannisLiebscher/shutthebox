package de.htwg.se.stb.diceComponent.persistence

import de.htwg.se.stb.diceComponent.DiceInterface
import de.htwg.se.stb.diceComponent.OneDice
import de.htwg.se.stb.diceComponent.TwoDice
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import concurrent.ExecutionContext.Implicits.global

object DiceDAOSQL extends  DiceDAO{
  val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")
  val diceSchema = TableQuery(new DiceTable(_))
  db.run(diceSchema.schema.create)
  def saveDice(dice: DiceInterface): Future[Int] = {
    val insertAction = diceSchema returning diceSchema.map(_.id) 
      += (None, dice.toString().head.asDigit, dice.toString().last.asDigit)
    db.run(insertAction)
  }


  def loadDice(id: Int): Future[DiceInterface] =  {
    val query = diceSchema.filter(_.id === id)
    val result = db.run(query.result.headOption)
    result.map {
      case Some((id, w1, w2)) => {
        if(w2 != 0) new TwoDice(w1,w2)
        else new OneDice(w1,w2)
      }
      case None => throw new Exception("Dice not found")
    }
  }
}
