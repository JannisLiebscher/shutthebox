package controller
import scala.util.{Try, Success, Failure}
import util.Command
import model.GameInterface

class ShutCommand(num: Int) extends Command[GameInterface]:
  override def noStep(game: GameInterface): Try[GameInterface] = Success(game)
  override def doStep(game: GameInterface): Try[GameInterface] = game.shut(num)
  override def undoStep(game: GameInterface): Try[GameInterface] = game.resShut(num)
  override def redoStep(game: GameInterface): Try[GameInterface] = game.shut(num)
