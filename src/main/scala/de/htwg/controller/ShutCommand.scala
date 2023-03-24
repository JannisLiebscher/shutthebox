package de.htwg.controller

import util.Command
import model.GameInterface

class ShutCommand(num: Int) extends Command[GameInterface]:
  override def noStep(game: GameInterface): GameInterface = game
  override def doStep(game: GameInterface): GameInterface = game.shut(num)
  override def undoStep(game: GameInterface): GameInterface = game.resShut(num)
  override def redoStep(game: GameInterface): GameInterface = game.shut(num)
