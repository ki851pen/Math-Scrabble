package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.cellComponent.Cell

import scala.collection.mutable


case class Grid(private val cells: Vector[Vector[Cell]]) {
  def this(size: Int) = this(Vector.tabulate(size, size) { (row, col) => Cell("") })

  val size: Int = cells.size

  def cell(row: Int, col: Int): Cell = cells(row)(col)

  def isEmpty: Boolean = cells.forall(v => v.forall(c => !c.isSet))

  def initSpecialCell: Grid = {
    val max: Int = size - 1
    val half: Int = max / 2
    Grid(Vector.tabulate(size, size) { (row, col) =>
      (row, col) match {
        case (a, b) if (a == 0 | a == max | a == half) & (b == 0 | b == max | b == half) => Cell("t", "")
        case (a, b) if a == b || a == max - b => Cell("d", "")
        case _ => Cell("n", "")
      }
    })
  }

  def clearCell(row: Int, col: Int): Grid = copy(cells.updated(row, cells(row).updated(col, cell(row, col).setCell(""))))

  def clearCells(posList: List[(Int, Int)]): Grid = {
    var grid = this
    posList.foreach(x => grid = grid.clearCell(x._1, x._2))
    grid
  }

  def getRow(row: Int): Vector[Cell] = cells(row)

  def getCol(col: Int): Vector[Cell] = cells.map(x => x(col))

  def getNeighborsOf(row: Int, col: Int): Map[(Int, Int), Cell] = {
    val neighbors = new mutable.TreeMap[(Int, Int), Cell]()
    if (!isOnTop(row) && cell(row - 1, col).isSet) neighbors.put((row - 1, col), cell(row - 1, col))
    if (!isExtremeLeft(col) && cell(row, col - 1).isSet) neighbors.put((row, col - 1), cell(row, col - 1))
    if (!isAtBottom(row) && cell(row + 1, col).isSet) neighbors.put((row + 1, col), cell(row + 1, col))
    if (!isExtremeRight(col) && cell(row, col + 1).isSet) neighbors.put((row, col + 1), cell(row, col + 1))
    neighbors.toMap
  }

  private def isOnTop(row: Int) = row == 0

  private def isExtremeLeft(col: Int) = col == 0

  private def isAtBottom(row: Int) = row == size - 1

  private def isExtremeRight(col: Int) = col == size - 1

  def set(row: Int, col: Int, value: String): Grid = Grid(cells.updated(row, cells(row).updated(col, cell(row, col).setCell(value))))

  override def toString: String = {
    val numCol = "      " + List.range(1, size + 1).filter(_ < 10).mkString("     ") + "    " + List.range(1, size + 1).filter(_ > 9).mkString("    ") + "  \n"
    val lineSeparator = "   +" + "-----+" * size + "\n"
    var box = "\n" + numCol + lineSeparator
    for (numLine <- 1 to size) {
      if (numLine < 10) {
        val line = String.format(" %s ", numLine) + "|" + "  _  |" * size + "\n"
        box += line + lineSeparator
      }
      else {
        val line = String.format(" %s", numLine) + "|" + "  _  |" * size + "\n"
        box += line + lineSeparator
      }
    }
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("_", cell(row, col).toString)
    box
  }
}
