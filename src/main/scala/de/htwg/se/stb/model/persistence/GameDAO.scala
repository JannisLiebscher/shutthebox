package de.htwg.se.stb.model.persistence

import de.htwg.se.stb.model.Game
import de.htwg.se.stb.model.GameInterface
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Failure
import scala.util.Success

import concurrent.ExecutionContext.Implicits.global

trait GameDAO {
  
  def deleteGame: Unit
  def updateGame(game: GameInterface): Unit
  def saveGame(game: GameInterface): Future[Int]
  def loadGame(id: Int): Future[GameInterface]
}
