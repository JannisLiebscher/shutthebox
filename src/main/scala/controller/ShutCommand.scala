package controller
import scala.util.{Try, Success, Failure}
import util.Command
import model.GameInterface

class ShutCommand(num: Int) extends Command[Try[GameInterface]]:
  override def noStep(game: Try[GameInterface]): Try[GameInterface] = game
  override def doStep(game: Try[GameInterface]): Try[GameInterface] = game.get.shut(num)
  override def undoStep(game: Try[GameInterface]): Try[GameInterface] = game.get.resShut(num)
  override def redoStep(game: Try[GameInterface]): Try[GameInterface] = game.get.shut(num)
