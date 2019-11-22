package de.htwg.se.scrabble.model

case class Player(name: String, hand: List[Card]){
  def this(name: String) = this(name, Nil)
  val maxHandSize:Int = 9

  def getHand: List[Card] = hand
  def getNrCardsInHand: Int = hand.size
  def addToHand(cards: List[Card]) : Player = copy(hand = hand ::: cards)
  def useCard(card: Card): Player = copy(hand = hand diff List(card))

  override def toString:String = name
}
