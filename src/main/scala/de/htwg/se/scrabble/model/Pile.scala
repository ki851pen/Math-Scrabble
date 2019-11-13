package de.htwg.se.scrabble.model
import scala.util.Random

case class Pile(tilepile: List[Any]){
  def this() = this(List.fill(20)("="):::List.fill(7)("+"):::List.fill(7)("-"):::List.fill(5)("*"):::List.fill(5)("/"):::List.fill(6)("_"):::List.fill(5)((0 to 9).toList).flatten)
  def this(equal:Int, plusminus:Int, muldiv:Int, blank:Int, digit:Int) = this(List.fill(equal)("="):::List.fill(plusminus)("+"):::List.fill(plusminus)("-"):::List.fill(muldiv)("*"):::List.fill(muldiv)("/"):::List.fill(blank)("_"):::List.fill(digit)((0 to 9).toList).flatten)
  def size: Int = tilepile.length
  def shuffle: Pile = Pile(Random.shuffle(tilepile))
  def take(value: Int): Pile = Pile(tilepile.drop(value))

  override def toString: String = tilepile.mkString(", ")
}
