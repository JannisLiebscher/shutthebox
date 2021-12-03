import model.*

val field = new Matrix[Int](4, 0)
field.replace(0, 0, 1)
field.replace(0, 1, 8)
val field2 = field.replace(1, 0, 9)
field.size
field2.cell(1, 1)
field2.cell(1, 0)

var wu = Dice("one")
wu.wuerfeln(2).toString()
wu.wuerfeln(2)
wu = wu.wuerfeln(2)
wu.toString()
wu.getSum()

var game = new Game()
game.board.shut(1)
var bo = new Board()
bo = bo.shut(2)
bo
bo.isShut(2)
bo.isShut(1)
bo.isShut(3)
val e = Some(1)
val z = Some(2)
val n = None
val res = e.getOrElse(0)
n.getOrElse(0)
e match {
  case Some(x) => x
}
val ga = Some(new Game)
ga.get

case class Ex(message: String) extends Exception()
