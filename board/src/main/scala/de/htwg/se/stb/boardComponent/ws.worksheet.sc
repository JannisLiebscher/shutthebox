import de.htwg.se.stb.boardComponent.BoardDAO.saveBoard
import de.htwg.se.stb.boardComponent.BoardDAO.toBinary
import de.htwg.se.stb.boardComponent.BoardDAO.fromBinary
import de.htwg.se.stb.boardComponent._
BoardDAO.toBinary(new Board().shut(8))

val test = 256.toBinaryString.split("")
fromBinary(511)

val b = new Board().shut(1).shut(5).shut(7).shut(3)
fromBinary(toBinary(b))
saveBoard(b)