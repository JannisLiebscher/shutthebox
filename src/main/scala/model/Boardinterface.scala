package model

trait BoardInterface {
  def shut(size: Int): BoardInterface
  def resShut(num: Int): BoardInterface
  def isShut(num: Int): Boolean
  def count(): Int
  def toString(): String
}
