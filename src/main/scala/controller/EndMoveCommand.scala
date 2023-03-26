package controller

import util.Command
import model.GameInterface
import model.Game

class EndMoveCommand extends Command[GameInterface]:
  var memento: GameInterface = new Game
  override def noStep(game: GameInterface): GameInterface = game
  override def doStep(game: GameInterface): GameInterface =
    memento = game
    game.endMove
  override def undoStep(game: GameInterface): GameInterface =
    val res = memento
    memento = game
    res
  override def redoStep(game: GameInterface): GameInterface =
    val res = memento
    memento = game
    res
