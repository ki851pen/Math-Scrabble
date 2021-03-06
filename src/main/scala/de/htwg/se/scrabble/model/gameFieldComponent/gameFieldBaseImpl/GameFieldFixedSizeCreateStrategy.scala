package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile

class GameFieldFixedSizeCreateStrategy(fixedSize: Int) extends GameFieldCreateStrategyTemplate {
  def this() = this(15)
  //Fixed sizes are the regularly used sizes like: 3, 5, 9...

  override def sizeGrid: Int = fixedSize

  override def pile: Pile = createAssociatedPileWithGivenFixedSize()

  private def createAssociatedPileWithGivenFixedSize(): Pile = {
    fixedSize match {
      case 3 => new Pile(3, 2, 2, 1)
      case 5 => new Pile(10, 3, 2, 2)
      case 9 => new Pile(15, 5, 4, 3)
      case 15 => new Pile()
    }
  }
}
