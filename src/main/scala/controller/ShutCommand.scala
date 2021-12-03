package controller

import util.Command
import model.Game

class ShutCommand(num: Int) extends Command[Game]:
  override def noStep(game: Game): Game = game
  override def doStep(game: Game): Game = game.shut(num)
  override def undoStep(game: Game): Game = game.resShut(num)
  override def redoStep(game: Game): Game = game.shut(num)
