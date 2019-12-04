package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.model.cell.Cell

case class Grid(private val cells:Vector[Vector[Cell]]) {
  def this(size:Int) = this(Vector.tabulate(size,size){(row,col) => Cell("")})
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells(row)(col)
  def isEmpty: Boolean = cells.forall(v => v.forall(c => !c.isSet))
  /*def setCellType(row:Int, col:Int, typ: String): Grid = typ match {
    case "n" | "d" | "t" => Grid(cells.updated(row, cells(row).updated(col, Cell(typ,cell(row,col).card.toString))))
    case _ => this
  }*/

  //def checkNeighbor(row:Int, col:Int): List[String] = cells(row-1)(col).value :: cells(row)(col-1).value :: cells(row+1)(col).value :: cells(row)(col+1).value :: Nil
  //def emptyNeighbor(row:Int, col:Int): Boolean = checkNeighbor(row,col).forall(s => s =="")
  /*def checkNeighbor(row:Int, col:Int): Boolean = {
    if (row != 0 && cells(row - 1)(col).isSet) {
      true
    }
    else if (col != 0 && cells(row)(col - 1).isSet) {
      true
    }
    else if (row != this.size - 1 && cells(row + 1)(col).isSet) {
      true
    }
    else if (col != this.size - 1 && cells(row)(col + 1).isSet) {
      true
    }
    else {
      false
    }
  }*/

  def set(row:Int, col:Int, value:String):Grid =  {
    /*if (!checkNeighbor(row, col) && !cells.forall(v => v.forall(c => !c.isSet))) {
      println("\nSteine koennen nur horizontal oder vertikal neben einem bereits auf dem Spielplan liegenden Stein platziert werden!\n" +
        "Bitte geben Sie eine neue Position an!\n")
      this
    } else {*/
      Grid(cells.updated(row, cells(row).updated(col, cell(row,col).setCell(value))))
    //}
  }

  def setEmpty(row:Int, col:Int):Grid =  {
    Grid(cells.updated(row, cells(row).updated(col, cell(row,col).makeEmpty)))
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
