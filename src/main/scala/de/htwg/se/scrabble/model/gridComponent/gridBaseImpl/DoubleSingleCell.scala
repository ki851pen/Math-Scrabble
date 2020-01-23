package de.htwg.se.scrabble.model.gridComponent.gridBaseImpl

import com.google.inject.Inject

private case class DoubleSingleCell(value: String) extends Cell(value: String) {
  val cellType: String = "d"

  def getPoint: Int = card.getPoint.getOrElse(0) * 2

  override def isDouble = true

  override def toString: String = if (isSet) {
    value
  } else {
    "x2"
  }
}
