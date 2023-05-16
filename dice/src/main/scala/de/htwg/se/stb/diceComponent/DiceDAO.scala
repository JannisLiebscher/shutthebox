package de.htwg.se.stb.diceComponent

import concurrent.ExecutionContext.Implicits.global
import de.htwg.se.stb.diceComponent.DiceInterface
import scala.concurrent.Future
import slick.jdbc.MySQLProfile.api._
import de.htwg.se.stb.diceComponent.DiceTable
import scala.util.Success
import scala.util.Failure
import de.htwg.se.stb.diceComponent.TwoDice
import de.htwg.se.stb.diceComponent.OneDice

object DiceDAO {
  val db = Database.forURL("jdbc:mariadb://localhost:3306/shutthebox", 
                         user = "test", 
                         password = "password", 
                         driver = "org.mariadb.jdbc.Driver")
  val diceSchema = TableQuery(new DiceTable(_))
  def create() = db.run(diceSchema.schema.create)
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
