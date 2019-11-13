package de.htwg.se.scrabble.model

case class Player(name: String, hand: List[Card]){
  def this(name: String) = this(name, Nil)
  val maxHandSize:Int = 9

  def getHand: List[Card] = hand
  def getNrCardsInHand: Integer = hand.size
  def addToHand(cards: List[Card]) =  Player(name, hand ::: cards)

  /*def addToHand(pile: Pile, nr : Int): Boolean = {
    if (getNrCardsInHand + nr <= handSize) {
      hand = pile.tilepile.take(nr) :: hand
      return true
    }
    false
  }*/

  override def toString:String = name
}
