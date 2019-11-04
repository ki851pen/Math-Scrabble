package de.htwg.se.scrabble.model

case class Card(value: Any) {
  val point: Map[Any, Int] = Map("="->1, "+"->1, "-"->1, "*"->2, "/"->3, "_"->0
    , 1->1, 2->1, 3->2, 4->2, 5->3, 6->2, 7->4, 8->2, 9->2, 0-> 1)

  val operator: List[String] = List("+","-","*","/","=")
  val validlist: List[String] = (0 to 9).toList.map(x => x.toString) ::: operator

  def isValid: Boolean = validlist.contains(value)
  def parseValue: Any = if(operator.contains(value)){value} else {value.toString.toIntOption.getOrElse("")}
  def getpoint: Int = point(value)

  override def toString: String = this.parseValue.toString
}