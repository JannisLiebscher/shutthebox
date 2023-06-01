package de.htwg.se.stb.model.fileioComponent

import de.htwg.se.stb.model.GameInterface

trait FileIOInterface {
  def load: GameInterface
  def save(grid: GameInterface): Unit
  def update(grid: GameInterface): Unit
  def delete: Unit
}
