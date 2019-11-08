package de.htwg.se.scrabble.model

case class Player(name: String){
  private var hand: List[Any] = Nil
  private val handSize = 9

  def getHand: List[Any] = hand
  def getNrCardsInHand: Integer = hand.size

  /*def addToHand(pile: Pile, nr : Int): Boolean = {
    if (getNrCardsInHand + nr <= handSize) {
      hand = pile.tilepile.take(nr) :: hand
      return true
    }
    false
  }*/

  override def toString:String = name
}
