package de.htwg.se.scrabble.model
import scala.util.Random

case class Pile(tilepile: List[Card]){
  def this() = this(List.fill(20)(Card("=")):::List.fill(7)(Card("+")):::List.fill(7)(Card("-")):::List.fill(5)(Card("*"))
    :::List.fill(5)(Card("/")):::List.fill(6)(Card("_")):::List.fill(5)((0 to 9).toList).flatten.map(x => Card(x.toString)))
  def this(equal:Int, plusminus:Int, muldiv:Int, blank:Int, digit:Int) =
    this(List.fill(equal)(Card("=")):::List.fill(plusminus)(Card("+")):::List.fill(plusminus)(Card("-")):::List.fill(muldiv)(Card("*"))
      :::List.fill(muldiv)(Card("/")):::List.fill(blank)(Card("_")):::List.fill(digit)((0 to 9).toList).flatten.map(x => Card(x.toString)))
  def size: Int = tilepile.length
  def shuffle: Pile = copy(Random.shuffle(tilepile))
  def drop(value: Int): Pile = copy(tilepile.drop(value))
  def take(value: Int): List[Card] = tilepile.take(value)
  def add(value:String, nr:Int): Pile = if (Card(value).isValid) Pile(tilepile ::: List.fill(nr)(Card(value))) else this

  override def toString: String = tilepile.mkString(", ")
}
