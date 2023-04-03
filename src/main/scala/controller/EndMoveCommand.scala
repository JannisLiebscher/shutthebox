package controller

import scala.util.{Try, Success, Failure}
import util.Command
import model.GameInterface
import model.Game

class EndMoveCommand extends Command[GameInterface]:
  var memento: GameInterface = new Game
  override def noStep(game: GameInterface): Try[GameInterface] = Success(game)
  override def doStep(game: GameInterface): Try[GameInterface] =
    memento = game
    game.endMove
  override def undoStep(game: GameInterface): Try[GameInterface] =
    val res = memento
    memento = game
    Success(res)
  override def redoStep(game: GameInterface): Try[GameInterface] =
    val res = memento
    memento = game
    Success(res)
