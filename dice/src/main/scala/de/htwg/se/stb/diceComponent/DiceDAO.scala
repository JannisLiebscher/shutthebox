package de.htwg.se.stb.diceComponent

import concurrent.ExecutionContext.Implicits.global
import de.htwg.se.stb.diceComponent.DiceInterface
import scala.concurrent.Future

trait DiceDAO {
  def saveDice(dice: DiceInterface): Future[Int]
  def loadDice(id: Int): Future[DiceInterface]
}
