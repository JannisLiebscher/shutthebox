package model.fileioComponent
import model.fileioComponent.FileIOInterface
import model.*
import scala.xml.{NodeSeq, PrettyPrinter}

class FileIOXML extends FileIOInterface {

  override def load: GameInterface = {
    var board = new Board(9)
    var playerSeq = Seq[Int]()
    val file = scala.xml.XML.loadFile("game.xml")
    var sumAtr = (file \\ "game" \ "sum").text.trim.toInt
    var sum = sumAtr
    var i = 1
    (file \\ "game" \ "board" \ "box").foreach(x =>
      if (x.text.equals(" true ")) board = board.shut(i)
      i += 1
    )
    playerSeq = playerSeq
      :+ (file \\ "game" \ "players" \ "score1").text.trim.toInt
      :+ (file \\ "game" \\ "players" \ "score2").text.trim.toInt
      :+ (file \\ "game" \ "players" \ "turn").text.trim.toInt
    var players = new Players(
      2,
      Vector(("Player 1", playerSeq(0)), ("Player 2", playerSeq(1))),
      playerSeq(2)
    )
    return new Game(board, Dice("two"), players, sum)
  }

  def save(game: GameInterface): Unit = saveString(game)
  def saveXML(game: GameInterface): Unit = {
    scala.xml.XML.save("game.xml", gameToXML(game))
  }
  def saveString(game: GameInterface) = {
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
        for (i <- (1 to 9)) yield { boardToXML(game, i) }
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
  def boardToXML(game: GameInterface, box: Int) = {
    <box>
        {game.isShut(box)}
    </box>
  }

}
