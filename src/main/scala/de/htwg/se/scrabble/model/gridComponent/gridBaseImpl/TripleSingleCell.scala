package de.htwg.se.scrabble.model.gridComponent.gridBaseImpl

import com.google.inject.Inject

private case class TripleSingleCell(value: String) extends Cell(value: String) {
  val cellType: String = "t"

  override def isTriple = true

  def getPoint: Int = card.getPoint.getOrElse(0) * 3

  override def toString: String = if (isSet) {
    value
  } else {
    "x3"
  }
}
