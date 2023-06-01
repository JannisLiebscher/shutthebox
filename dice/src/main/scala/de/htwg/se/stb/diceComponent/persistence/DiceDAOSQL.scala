package de.htwg.se.stb.diceComponent.persistence

import de.htwg.se.stb.diceComponent.DiceInterface
import de.htwg.se.stb.diceComponent.OneDice
import de.htwg.se.stb.diceComponent.TwoDice
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.util.Failure
import scala.util.Success

import concurrent.ExecutionContext.Implicits.global
import com.typesafe.config.ConfigFactory

object DiceDAOSQL extends  DiceDAO{
  val config = ConfigFactory.load()
  val url = config.getString("mariadb.url")
  val user = config.getString("mariadb.user")
  val pw = config.getString("mariadb.password")
  val db = Database.forURL(url, user, pw, driver = "org.mariadb.jdbc.Driver")
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
