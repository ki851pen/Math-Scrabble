package de.htwg.se.scrabble.model.gridComponent.gridBaseImpl

import com.google.inject.Inject
import de.htwg.se.scrabble.model.gridComponent.CardInterface

case class Card @Inject() (value: String) extends CardInterface {
  private val point: Map[String, Int] = Map("=" -> 1, "+" -> 1, "-" -> 1, "*" -> 2, "/" -> 3, "?" -> 0
    , "1" -> 1, "2" -> 1, "3" -> 2, "4" -> 2, "5" -> 3, "6" -> 2, "7" -> 4, "8" -> 2, "9" -> 2, "0" -> 1)
  val validSet: Set[String] = point.keySet
  val parseValue: Any = if (isValid) {
    value.toString.toIntOption.getOrElse(value)
  } else {
    ""
  }

  def isValid: Boolean = validSet.contains(value)

  def isDigit: Boolean = parseValue.isInstanceOf[Int]

  def isQuestionMark: Boolean = value == "?"

  def isOperator: Boolean = !isQuestionMark && !isDigit

  def getPoint: Option[Int] = {
    try {
      Some(point(value))
    } catch {
      case _: Exception => None
    }
  }

  override def toString: String = this.parseValue.toString

  override def equals(obj: Any): Boolean = obj match {
    case c: Card => c.parseValue == parseValue
    case _ => false
  }
}
