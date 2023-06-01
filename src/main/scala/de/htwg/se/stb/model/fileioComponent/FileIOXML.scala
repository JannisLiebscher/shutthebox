package de.htwg.se.stb.model.fileioComponent
import de.htwg.se.stb.boardComponent.*
import de.htwg.se.stb.diceComponent.*
import de.htwg.se.stb.playerComponent.*
import de.htwg.se.stb.model.fileioComponent.FileIOInterface
import de.htwg.se.stb.model.*
import scala.xml.{NodeSeq, PrettyPrinter}

class FileIOXML extends FileIOInterface {

  override def delete: Unit = ???
  override def update(game: GameInterface): Unit = ???
  override def load: GameInterface = {
    val file = scala.xml.XML.loadFile("game.xml")

    val sum = (file \\ "game" \ "sum").text.trim.toInt

    val boardstate = (file \\ "game" \ "board" \ "box")
    val board: BoardInterface = (0 to 8)
    .filter(i => boardstate(i).text.trim.toBoolean)
    .map(i => (b: BoardInterface) => b.shut(i + 1)).toList
    .foldLeft(new Board(): BoardInterface)((board, func) => func(board))

    val playerSeq = Seq[Int](
      (file \\ "game" \ "players" \ "score1").text.trim.toInt,
      (file \\ "game" \\ "players" \ "score2").text.trim.toInt,
      (file \\ "game" \ "players" \ "turn").text.trim.toInt
    )
    val players = new Players(
      2,
      Vector(("Player 1", playerSeq(0)), ("Player 2", playerSeq(1))),
      playerSeq(2)
    )
    return new Game(board, Dice("two"), players, sum)
  }

  def save(game: GameInterface) = {
    import java.io._
    val pw = new PrintWriter(new File("game.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameToXML(game))
    pw.write(xml)
    pw.close
  }

  def gameToXML(game: GameInterface) = {
    <game>{
      <board>
        {
        for (i <- (1 to 9)) yield { boxToXML(game, i) }
      }
        </board>
        <players>
            <score1>
                {game.getScore(1)}
            </score1>
            <score2>
                {game.getScore(2)}
            </score2>
            <turn>
                {game.getTurn}
            </turn>
        </players>
        <sum>
            {game.getSum}
        </sum>
    }
      </game>

  }
  def boxToXML(game: GameInterface, box: Int) = {
    <box>
        {game.isShut(box)}
    </box>
  }

}
