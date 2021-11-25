import scala.io.StdIn.readLine

object shutthebox {
  @main def run: Unit =
    /* println("Welcome!")
    println("Enter HP between 0 and 10")
    val health = readLine.toInt
    val mesh = (bar(width) + cells(width)) * zeilen + bar(width)
    println(mesh)
    print("HP:" + healthbar(health) + "\n") */
    val c =
      new controller.Controller(new model.Board(9), new model.Dice())
    val tui = new aview.Tui(c)
    tui.run

  val width = 1
  val zeilen = 1
  val spalten = 20
  def cells(cellwidth: Int = 3) = ("|" + " " * cellwidth) * spalten + "|\n"
  def bar(cellwidth: Int = 3) = ("+" + "-" * cellwidth) * spalten + "+\n"

  def healthbar(hp: Int = 0): String =
    ("|" + "+ " * hp) + "  " * (10 - hp) + "|"
}
