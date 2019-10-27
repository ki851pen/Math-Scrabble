package de.htwg.se.scrabble.model
import scala.util.Random

case class Pile(mode: Int){

  val tilepile: List[Any] = List.fill(20)("="):::List.fill(7)("+"):::List.fill(7)("-"):::List.fill(5)("*"):::List.fill(5)("/"):::List.fill(6)("_"):::List.fill(5)((0 to 9).toList).flatten
  def size: Int = tilepile.length
  def shuffle: Pile = Random.shuffle(tilepile)

  override def toString: String = tilepile.toString()
}
