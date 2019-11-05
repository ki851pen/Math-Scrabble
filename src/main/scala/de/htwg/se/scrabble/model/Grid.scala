package de.htwg.se.scrabble.model

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, new Cell("")))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def isEmpty: Boolean = cells.rows.forall(c => c.forall(d => d.value == ""))
  def set(row:Int, col:Int, value:String):Grid = copy(cells.replaceCell(row, col, Cell(value, cell(row, col).special)))

  override def toString: String = {
    val numCol = "      " + List.range(1, size + 1).filter(_<10).mkString("    ") + "    " + List.range(1, size + 1).filter(_>9).mkString("   ") +  "  \n"
    val lineSeparator = "   +" + "-----" * size + "+\n"
    var box = "\n" + numCol + lineSeparator
    for (numLine <- 1 to size){
      if(numLine <10){
        val line = String.format(" %s  ", numLine) + "|" + " _  |"  * size + "\n"
        box += line}
      else {
        val line = String.format(" %s ", numLine) + "|" + " _  |"  * size + "\n"
        box += line
      }
    }
    box += lineSeparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("_", cell(row, col).toString)
    box
  }
}
