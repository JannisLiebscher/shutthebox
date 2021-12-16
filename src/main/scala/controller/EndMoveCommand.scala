package controller

import util.Command
import model.GameInterface
import model.Game

class EndMoveCommand extends Command[GameInterface]:
  var afterDo = new Game
  var beforeDo = new Game
  override def noStep(game: GameInterface): GameInterface = game
  override def doStep(game: GameInterface): GameInterface =
    beforeDo = game
    afterDo = game.endMove
    afterDo
  override def undoStep(game: GameInterface): GameInterface = beforeDo
  override def redoStep(game: GameInterface): GameInterface = afterDo
