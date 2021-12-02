package controller
import util.Observable
import model.Dice
import model.Board
import model.Game
case class Controller(var game: Game) extends Observable:

  override def toString(): String = game.toString()

  def doAndPublish(doThis: => Game) =
    game = doThis
    notifyObservers
  def doAndPublish(doThis: Int => Game, num: Int) =
    game = doThis(num)
    notifyObservers

  def wuerfeln: Game =
    if (game.count() <= 6) game = game.wuerfeln(1)
    else game = game.wuerfeln(2)
    return game

  def shut(num: Int): Game =
    game = game.shut(num)
    return game

  def endMove: Game =
    game = game.endMove()
    return game
