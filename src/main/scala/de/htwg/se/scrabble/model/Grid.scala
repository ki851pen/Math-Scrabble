package de.htwg.se.scrabble.model

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, new Cell("")))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:String):Grid = copy(cells.replaceCell(row, col, Cell(value, cell(row, col).special)))

  override def toString: String = {
    val numCol = "     " + List.range(1, size + 1).mkString("   ") +  "  \n"
    val lineSeparator = "   +" + "----" * size + "+\n"
    var box = "\n" + numCol + lineSeparator
    for (numLine <- 1 to size){
      val line = String.format(" %s ", numLine) + "|" + " _ |"  * size + "\n"
      box += line
    }
    box += lineSeparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("_", cell(row, col).toString)
    box
  }
}