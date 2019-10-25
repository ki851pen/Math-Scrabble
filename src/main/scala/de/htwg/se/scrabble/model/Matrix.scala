package de.htwg.se.scrabble.model

case class Matrix[T] (rows:Vector[Vector[T]]){
  def this(size : Int, cell : T) = this(Vector.tabulate(size,size){(row,col) => cell})
  val size:Int = rows.size

  def cell(row: Int, col: Int): T = rows(row)(col)
  def fill(filling: T): Matrix[T] =
    copy(Vector.tabulate(size, size) { (row, col) => filling})

  def replaceCell(row: Int, col: Int, cell: T): Matrix[T] =
    copy(rows.updated(row, rows(row).updated(col, cell)))
}
