package de.htwg.se.stb.util

trait Observer:
  def update: Unit
  def handle(error: Throwable): Unit

trait Observable:
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer) = subscribers = subscribers :+ s
  def remove(s: Observer) = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers = subscribers.foreach(o => o.update)
  def raiseError(error: Throwable) = subscribers.foreach(o => o.handle(error))
