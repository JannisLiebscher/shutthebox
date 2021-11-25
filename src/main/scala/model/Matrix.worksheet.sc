import model.Matrix
import model.Stone

val field = new Matrix[Stone](4, Stone.EMPTY)
field.replace(0, 0, Stone.ONE)
field.replace(0, 1, Stone.EIGHT)
val field2 = field.replace(1, 0, Stone.NINE)
field.size
field2.cell(1, 1).getName
field2.cell(1, 0).getName
import model.Dice
val wu = new Dice()
val a = wu.wuerfeln(2)
wu.getSum()

wu
import model.Board

var bo = new Board()
bo.count()
bo.shut(3)
bo
bo.count()
bo.shut(1)
bo.toString
print(bo)
import aview.Tui
import controller.Controller
val c = new Controller(new Board(4), new Dice())
c.wuerfeln()
c.w
c.toString()
c.board.count()
c.board.shut(4).shut(3).toString()
c.board.shut(3).toString()
c.toString()
c.board.count()
c.wuerfeln()
c.w
