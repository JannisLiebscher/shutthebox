package de.htwg.se.stb.boardComponent

case class Matrix[T](rows: Vector[Vector[T]]) {
  def this(size: Int, filling: T) =
    this(Vector.tabulate(size, 2) { (rows, col) => filling })

  val size: Int = rows.size

  def cell(row: Int, col: Int): T = rows(row)(col)

  def row(num: Int): Vector[T] = rows.map(x => x(0))

  def fill(filling: T): Matrix[T] = copy(Vector.tabulate(size, size) {
    (row, col) => filling
  })

  def replace(row: Int, col: Int, fill: T): Matrix[T] = copy(
    rows.updated(row, rows(row).updated(col, fill))
  )
}