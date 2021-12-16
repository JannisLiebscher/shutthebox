package controller

import util.Command
import model.Game
import model.GameInterface

class WuerfelnCommand(num: Int) extends Command[GameInterface]:
  var afterDo: GameInterface
  var beforeDo: GameInterface
  override def noStep(game: GameInterface): GameInterface = game
  override def doStep(game: GameInterface): GameInterface =
    beforeDo = game
    afterDo = game.wuerfeln(num)
    afterDo
  override def undoStep(game: GameInterface): GameInterface = beforeDo
  override def redoStep(game: GameInterface): GameInterface = afterDo
