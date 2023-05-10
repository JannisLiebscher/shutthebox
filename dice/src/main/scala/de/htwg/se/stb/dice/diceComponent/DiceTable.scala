package de.htwg.se.stb.diceComponent

import slick.lifted.Tag
import slick.jdbc.MySQLProfile.api._

class DiceTable(tag: Tag) extends Table[DiceInterface](tag, "dice") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def w1 = column[Int]("w1")
  def w2 = column[Int]("w2")
  
  def * = (w1, w2) <> (diceFromTuple, diceToTuple)

  private def diceFromTuple(tuple: (Int, Int)): DiceInterface = (tuple: @unchecked) match {
    case (w1, w2) if w2 != 0 => TwoDice(w1, w2)
    case (w1, 0)             => OneDice(w1, 0)
  }

  private def diceToTuple(dice: DiceInterface): Option[(Int, Int)] = dice match {
    case TwoDice(w1, w2) => Some((w1, w2))
    case OneDice(w1, 0)  => Some((w1, 0))
    case _               => None
  }
}
