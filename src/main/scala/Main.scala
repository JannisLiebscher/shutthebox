import scala.io.StdIn.readLine
import model.Game
import controller.ControllerInterface
object shutthebox {
  @main def run: Unit =
    val c: ControllerInterface =
      new controller.Controller(new Game())
    val tui = new aview.Tui(c)
    val gui = new aview.Gui(c)

    var input: String = ""
    while (input != "q") {
      input = readLine()
      tui.getInputAndPrintLoop(input)
    }
}
