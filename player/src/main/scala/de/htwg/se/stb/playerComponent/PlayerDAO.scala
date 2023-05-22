package de.htwg.se.stb.playerComponent

import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait PlayerDAO {
  def savePlayer(player: PlayerInterface): Future[Int]
  def loadPlayer(id: Int): Future[PlayerInterface]
}
