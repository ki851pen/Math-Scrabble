package de.htwg.se.scrabble.model

case class Alphabet() {
  val point: Map[String, Int] = Map("a"->1, "b"->3, "c"->3, "d"->2, "e"->1, "f"->4
    , "g"->2, "h"->4, "i"->1, "j"->8, "k"->5, "l"->1, "m"->3, "n"->1
    , "o"->1, "p"->3, "q"->10, "r"->1, "s"->1, "t"->1, "u"->1, "v"->4
    , "w"->4, "x"->8, "y"->4, "z"->10)
}

class alphalist()

class Grid(rows: Int,cols: Int) {
  private val mygrid =  Array.ofDim[Cell](rows,cols)
}

class Cell(char: String){
  private val letter = char
  //def isSet {}
  def isAlphabet: Boolean = {'a' to 'z' contains letter}
  def getPoint: Int = {
    var a = Alphabet()
    a.point(char)
  }
}

class doublecell(char: String) extends Cell(char) {
  override def getPoint: Int = super.getPoint * 2
}

class Hand(number: Int) {

}

