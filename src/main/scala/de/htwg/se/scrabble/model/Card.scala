package de.htwg.se.scrabble.model

case class Card(value: String) {
  val point: Map[String, Int] = Map("="->1, "+"->1, "-"->1, "*"->2, "/"->3, "?"->0
    , "1"->1, "2"->1, "3"->2, "4"->2, "5"->3, "6"->2, "7"->4, "8"->2, "9"->2, "0"-> 1)
  val validSet: Set[String] = point.keySet
  val parseValue: Any = if(isValid){value.toString.toIntOption.getOrElse(value)} else {""}

  def isValid: Boolean = validSet.contains(value)
  def getPoint: Option[Int] = {
    try {
      Some(point(value))
    } catch {
      case Exception => None
    }
  }

  override def toString: String = this.parseValue.toString
}