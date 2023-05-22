package de.htwg.se.stb.boardComponent

import de.htwg.se.stb.boardComponent.BoardTable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

trait BoardDAO {
  def saveBoard(board: BoardInterface): Future[Int]
  def loadBoard(id: Int): Future[BoardInterface]
}
