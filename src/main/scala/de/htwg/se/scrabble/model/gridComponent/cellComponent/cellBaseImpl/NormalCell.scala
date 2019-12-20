package de.htwg.se.scrabble.model.gridComponent.cellComponent.cellBaseImpl


private case class NormalCell(value: String) extends Cell(value: String) {
  val cellType: String = "n"

  override def isNormal = true

  def getPoint: Int = card.getPoint.getOrElse(0)

  override def toString: String = if (isSet) {
    value
  } else {
    " "
  }
}