package de.htwg.model.fileioComponent
import model.fileioComponent.FileIOInterface
import model.*
import play.api.libs.json._
import scala.io.Source

class FileIOJSON extends FileIOInterface {

  override def load: GameInterface = {
    var board: BoardInterface = new Board()
    var playersSeq = Seq[Int]()
    val source: String = Source.fromFile("game.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val sum = (json \ "game" \ "sum").get.toString.toInt
    playersSeq = playersSeq
      :+ (json \ "game" \ "players" \ "score1").get.toString.toInt
      :+ (json \ "game" \ "players" \ "score2").get.toString.toInt
      :+ (json \ "game" \ "players" \ "turn").get.toString.toInt
    var players = new Players(
      2,
      Vector(("Player 1", playersSeq(0)), ("Player 2", playersSeq(1))),
      playersSeq(2)
    )
    // val boardstate = (json \ "game" \ "board").get.toString.split(",")
    // boardstate(0) = boardstate(0).slice(1, 30)
    // boardstate(boardstate.length - 1) = boardstate(boardstate.length - 1)
    //   .slice(0, boardstate(boardstate.length - 1).length - 1)

    // for (i <- (0 to 8))
    //   if (boardstate(i).equals("true")) board = board.shut(i + 1)
    val boardstate = (json \ "game" \\ "board")(0).asInstanceOf[JsArray]
    for (i <- (0 to 8))
      if (boardstate(i).toString.toBoolean) board = board.shut(i + 1)
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
    var seq = Seq[Boolean]()
    for (i <- (1 to 9)) seq = seq :+ game.isShut(i)
    return seq
  }
  def playersToJson(game: GameInterface) = {
    Json.obj(
      "score1" -> JsNumber(game.getScore(1)),
      "score2" -> JsNumber(game.getScore(2)),
      "turn" -> JsNumber(game.getTurn)
    )
  }
}
