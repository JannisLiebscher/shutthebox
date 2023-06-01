package de.htwg.se.stb.playerComponent.persistence

import de.htwg.se.stb.playerComponent.*

import scala.concurrent.Future

import concurrent.ExecutionContext.Implicits.global

trait PlayerDAO {
  def savePlayer(player: PlayerInterface): Future[Int]
  def loadPlayer(id: Int): Future[PlayerInterface]
}
