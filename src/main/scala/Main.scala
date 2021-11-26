import scala.io.StdIn.readLine
import model.*
object shutthebox {
  @main def run: Unit =
    val c =
      new controller.Controller(new Game())
    val tui = new aview.Tui(c)

    var input: String = ""
    while (input != "q") {
      input = readLine()
      tui.getInputAndPrintLoop(input)
    }
}
