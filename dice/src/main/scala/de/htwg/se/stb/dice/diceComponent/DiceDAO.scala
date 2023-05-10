package de.htwg.se.stb.dice.diceComponent

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

  def saveDice(dice: DiceInterface): Future[Int] =  {
    val insertAction = diceSchema += (None, dice.toString().head.toInt, dice.toString().last.toInt)
    return db.run(insertAction)
  }
  def loadDice(id: Int): DiceInterface =  {
    val query = diceSchema.filter(_.id === id)
    val result = db.run(query.result.headOption)
    result.onComplete {
      case Success(Some((id, w1, w2))) => {
        if(w2 != 0) return new TwoDice(w1,w2)
        else return new OneDice(w1,w2)
      }
      case Success(None) => None
      case Failure(error) => println(s"Fehler beim Abrufen des Datensatzes: $error")
    }
  }
}
