package de.htwg.se.scrabble.model

case class Grid(private val cells:Vector[Vector[Cell]]) {
  def this(size:Int) = this(Vector.tabulate(size,size){(row,col) => Cell("n","")})
  //def this(size:Int, filling: Cell) = this(Vector.tabulate(size,size) {(row,col) => filling})
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells(row)(col)
  def isEmpty: Boolean = cells.forall(v => v.forall(c => !c.isSet))

  //def checkNeighbor(row:Int, col:Int): List[String] = cells(row-1)(col).value :: cells(row)(col-1).value :: cells(row+1)(col).value :: cells(row)(col+1).value :: Nil
  //def emptyNeighbor(row:Int, col:Int): Boolean = checkNeighbor(row,col).forall(s => s =="")
  //TODO: man kann cell nur setzen wenn eine Nachbarn Cell ein Wert besitzt
  def set(row:Int, col:Int, value:String):Grid = copy(cells.updated(row, cells(row).updated(col, cells(row)(col).setCell(value))))

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
