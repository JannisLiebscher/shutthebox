import scala.io.StdIn.readLine

object shutthebox {
  @main def run: Unit =
    val c =
      new controller.Controller(new model.Board(9), new model.Dice())
    val tui = new aview.Tui(c)

    var input: String = ""
    while (input != "q") {
      input = readLine()
      tui.getInputAndPrintLoop(input)
    }
}
