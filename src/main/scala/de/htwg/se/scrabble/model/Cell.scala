package de.htwg.se.scrabble.model

import de.htwg.se.scrabble.util.SpecialCell._
/*case class Cell(value: String, special: SpecialCell){
  def this(value: String) = this(value, Normal)
  val card = Card(value)
  def isNormal : Boolean = special == Normal
  def isSet : Boolean = value != ""

  override def toString: String = {
    if(isSet) {value} else{" "}
  }
}*/

trait Cell {
  def isSet: Boolean
  def getPoint: Int
  def setCell(newValue: String): Cell
}

private class NormalCell(value: String) extends Cell {
  override def isSet: Boolean = value != ""
  override def getPoint: Int = Card(value).getPoint
  override def setCell(newValue: String): Cell = Cell("n",value)
}

private class DoubleSingleCell(value: String) extends Cell {
  override def isSet: Boolean = value != ""
  override def getPoint: Int = Card(value).getPoint * 2
  override def setCell(newValue: String): Cell = Cell("d",value)
}

private class TripleSingleCell(value: String) extends Cell {
  override def isSet: Boolean = value != ""
  override def getPoint: Int = Card(value).getPoint * 3
  override def setCell(newValue: String): Cell = Cell("t",value)
}

object Cell {
  def apply(kind: String, value: String): Cell = kind match {
    case "n" => new NormalCell(value)
    case "d" => new DoubleSingleCell(value)
    case "t" => new TripleSingleCell(value)
  }
}
