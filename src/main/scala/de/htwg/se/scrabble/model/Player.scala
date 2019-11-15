package de.htwg.se.scrabble.model

case class Player(name: String, hand: List[Card]){
  def this(name: String) = this(name, Nil)
  val maxHandSize:Int = 9

  def getHand: List[Card] = hand
  def getNrCardsInHand: Int = hand.size
  def addToHand(cards: List[Card]) : Player ={
    Player(name, hand ::: cards)
  }

  override def toString:String = name
}
