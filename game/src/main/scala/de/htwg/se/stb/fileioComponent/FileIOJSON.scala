package de.htwg.se.stb.model.fileioComponent
import de.htwg.se.stb.boardComponent.*
import de.htwg.se.stb.diceComponent.*
import de.htwg.se.stb.playerComponent.*
import de.htwg.se.stb.model.fileioComponent.FileIOInterface
import de.htwg.se.stb.model.*
import play.api.libs.json._
import scala.io.Source

class FileIOJSON extends FileIOInterface {

  override def load: GameInterface = {
    val source: String = Source.fromFile("game.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    
    val sum = (json \ "game" \ "sum").get.toString.toInt
    val playersSeq = Seq[Int](
      (json \ "game" \ "players" \ "score1").get.toString.toInt,
      (json \ "game" \ "players" \ "score2").get.toString.toInt,
      (json \ "game" \ "players" \ "turn").get.toString.toInt
    )
    val players = new Players(
      2,
      Vector(("Player 1", playersSeq(0)), ("Player 2", playersSeq(1))),
      playersSeq(2)
    )
    val boardstate = (json \ "game" \\ "board")(0).asInstanceOf[JsArray]
    val board: BoardInterface = (0 to 8)
      .filter(i => boardstate(i).toString.toBoolean)
      .foldLeft(new Board(): BoardInterface)((acc, i) => acc.shut(i + 1))
    return new Game(board, Dice("two"), players, sum)
  }

  override def save(game: GameInterface) = {
    import java.io._
    val pw = new PrintWriter(new File("game.json"))
    pw.write(Json.prettyPrint(gameToJson(game)))
    pw.close
  }
  def gameToJson(game: GameInterface) = {
    Json.obj(
      "game" -> Json.obj(
        "board" -> Json.toJson(boardToJson(game)),
        "players" -> Json.toJson(playersToJson(game)),
        "sum" -> JsNumber(game.getSum)
      )
    )
  }
  def boardToJson(game: GameInterface): Seq[Boolean] = {
    (1 to 9).map(x => game.isShut(x)).toSeq
  }
  def playersToJson(game: GameInterface) = {
    Json.obj(
      "score1" -> JsNumber(game.getScore(1)),
      "score2" -> JsNumber(game.getScore(2)),
      "turn" -> JsNumber(game.getTurn)
    )
  }
}
