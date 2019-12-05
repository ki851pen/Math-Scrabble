package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.cell.Cell

case class Grid(private val cells:Vector[Vector[Cell]]) {
  def this(size:Int) = this(Vector.tabulate(size,size){(row,col) => Cell("")})
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells(row)(col)
  def isEmpty: Boolean = cells.forall(v => v.forall(c => !c.isSet))
  def initSpecialCell: Grid = {
    val max: Int = size - 1
    val half: Int = max / 2
    Grid(Vector.tabulate(size,size){(row,col) => (row,col) match {
      case (a,b) if (a==0 | a ==max | a ==half) & (b==0 | b ==max | b==half) => Cell("t","")
      case (a,b) if a == b || a == max-b => Cell("d","")
      case _ => Cell("n","")
    }})
  }
  def getRow(row: Int): Vector[Cell] = cells(row)
  def getCol(col: Int): Vector[Cell] = cells.map(x => x(col))
  /*
  def getNeighbors (row : Int, col: Int): List[Cell] = {
    val ROW = List(row - 1, row, row + 1).filter(_ > - 1).filter(_ < size)
    val COL = List(col - 1, col, col + 1).filter(_ > - 1).filter(_ < size)
    println(ROW)
    println(COL)
    for {
      nrow <- ROW
      ncol <- COL
    } yield cell(nrow, ncol)
  }*/

  /*def checkCorners(row: Int, col: Int) = {
    getNeighbors(row, col)
  }*/

    /*var corners = List(1,2,3,4)
    if (row == 0) {
      corners = corners.filter(x => x == 1 | x == 2)
    }
    if (col == 0) {
      corners = corners.filter(x => x == 1 | x == 4)
    }
    if (row == size - 1) {
      corners = corners.filter(x => x == 3 | x == 4)
    }
    if (col == size - 1) {
      corners = corners.filter(x => x == 2 | x == 3)
    }
    corners.forall(nr => checkCorner(row, col, nr))

  def checkCorner(row: Int, col: Int, nr: Int): Boolean ={
    nr match {

    }
  }*/

  def set(row:Int, col:Int, value:String):Grid =  {
    //if set have to have at least one neighbor. cant have 3 neighbors in the corner
    Grid(cells.updated(row, cells(row).updated(col, cell(row,col).setCell(value))))
  }

  override def toString: String = {
    val numCol = "      " + List.range(1, size + 1).filter(_<10).mkString("     ") + "    " + List.range(1, size + 1).filter(_>9).mkString("    ") +  "  \n"
    val lineSeparator = "   +" + "-----+" * size + "\n"
    var box = "\n" + numCol + lineSeparator
    for (numLine <- 1 to size){
      if(numLine <10){
        val line = String.format(" %s ", numLine) + "|" + "  _  |"  * size + "\n"
        box += line + lineSeparator}
      else {
        val line = String.format(" %s", numLine) + "|" + "  _  |"  * size + "\n"
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
