package controller

import scala.util.{Try, Success, Failure}
import util.Command
import model.GameInterface
import model.Game

class EndMoveCommand extends Command[Try[GameInterface]]:
  var memento: GameInterface = new Game
  override def noStep(game: Try[GameInterface]): Try[GameInterface] = game
  override def doStep(game: Try[GameInterface]): Try[GameInterface] =
    memento = game.get
    game.get.endMove
  override def undoStep(game: Try[GameInterface]): Try[GameInterface] =
    val res = memento
    memento = game.get
    Success(res)
  override def redoStep(game: Try[GameInterface]): Try[GameInterface] =
    val res = memento
    memento = game.get
    Success(res)
