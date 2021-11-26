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
val c = new Controller(new Game())
c.wuerfeln()
c.toString()

c.shut(4).shut(3).toString()
c.shut(3).toString()
c.toString()
c.wuerfeln()
var pl = new Players(1)
