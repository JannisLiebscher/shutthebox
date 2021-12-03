package controller

import util.Command
import model.Game

class WuerfelnCommand(num: Int) extends Command[Game]:
  var afterDo = new Game
  var beforeDo = new Game
  override def noStep(game: Game): Game = game
  override def doStep(game: Game): Game =
    beforeDo = game
    afterDo = game.wuerfeln(num)
    afterDo
  override def undoStep(game: Game): Game = beforeDo
  override def redoStep(game: Game): Game = afterDo
