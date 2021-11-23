import model.Matrix
import model.Stone

val field = new Matrix[Stone](4, Stone.EMPTY)
field.replace(0, 0, Stone.ONE)
field.replace(0, 1, Stone.EIGHT)
val field2 = field.replace(1, 0, Stone.NINE)
field.size
field2.cell(1, 1).getName
field2.cell(1, 0).getName
import model.Wuerfel
val wu = new Wuerfel
val a = wu.wuerfeln(2)
wu.getSum()
val b = wu.wuerfeln(1)
wu.getSum()
import model.Board

var bo = new Board()
bo.count()
bo = bo.shut(1)
bo = bo.shut(3)
bo
bo.count()
bo = bo.shut(1)
bo.toString
print(bo)
