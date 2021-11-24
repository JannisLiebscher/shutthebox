package model
import model.Matrix
import model.Stone
case class Board private (private var m: Matrix[Int]) {

  def this(size: Int = 9) =
    this(new Matrix[Int](size, 0))
    for (n <- (0 to m.size - 1)) m = m.replace(n, 0, n + 1)

  def shut(num: Int): Unit =
    if (m.cell(num - 1, 0) != 0)
      m = m.replace(num - 1, 0, 0).replace(num - 1, 1, num)
    else print("Stein " + num + " ist schon unten!")

  def count(): Int =
    var sum = 0
    for (n <- (0 to m.size - 1)) sum += m.cell(n, 0)
    return sum

  override def toString(): String =
    var out = "| "
    for (n <- (1 to m.size))
      if (m.cell(n - 1, 0) != 0)
        out = out + n + " | "
      else
        out = out + "#" + " | "
    out = out + sys.props("line.separator") + "| "
    for (n <- (1 to m.size)) {
      if (m.cell(n - 1, 1) != 0) out = out + n + " | "
      else
        out = out + "#" + " | "
    }
    return out
}
