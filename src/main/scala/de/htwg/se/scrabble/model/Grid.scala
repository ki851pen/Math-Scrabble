package de.htwg.se.scrabble.model

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, new Cell("")))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, cell:Cell):Grid = copy(cells.replaceCell(row, col, cell))
}
