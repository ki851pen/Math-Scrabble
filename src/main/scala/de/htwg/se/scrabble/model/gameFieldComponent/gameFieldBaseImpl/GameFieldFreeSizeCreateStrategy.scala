package de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl

import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile

class GameFieldFreeSizeCreateStrategy(sizeGridParam: Int, pileParam: Pile) extends GameFieldCreateStrategyTemplate {
  def this(sizeGrid: Int, equal: Int, plusminus: Int, muldiv: Int, digit: Int) =
    this(sizeGrid, new Pile(equal, plusminus, muldiv, digit))

  override def sizeGrid: Int = sizeGridParam

  override def pile: Pile = pileParam
}
