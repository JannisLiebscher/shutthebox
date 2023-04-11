package de.htwg.se.stb.controller

import scala.util.{Try, Success, Failure}
import de.htwg.se.stb.util.Command
import de.htwg.se.stb.model.Game
import de.htwg.se.stb.model.GameInterface

class WuerfelnCommand(num: Int) extends Command[GameInterface]:
  var memento: GameInterface = new Game
  override def noStep(game: GameInterface): Try[GameInterface] = Success(game)
  override def doStep(game: GameInterface): Try[GameInterface] =
    memento = game
    game.wuerfeln(num)
  override def undoStep(game: GameInterface): Try[GameInterface] = 
    val res = memento
    memento = game
    Success(res)
  override def redoStep(game: GameInterface): Try[GameInterface] = 
    val res = memento
    memento = game
    Success(res)
