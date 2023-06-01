package de.htwg.se.stb.boardComponent.persistence

import de.htwg.se.stb.boardComponent.*

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait BoardDAO {
  def saveBoard(board: BoardInterface): Future[Int]
  def loadBoard(id: Int): Future[BoardInterface]
}
