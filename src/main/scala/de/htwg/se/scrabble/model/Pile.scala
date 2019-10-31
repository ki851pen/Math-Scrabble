package de.htwg.se.scrabble.model

case class Pile(){
  //def this(equal:Int,plus:Int,minus:Int,mul:Int,div:Int,blank:Int,digit:Int)
  //Vector oder List?
  val tilepile: List[Any] = List.fill(20)("="):::List.fill(7)("+"):::List.fill(7)("-"):::List.fill(5)("*"):::List.fill(5)("/"):::List.fill(6)("_"):::List.fill(5)((0 to 9).toList).flatten
  def size: Int = tilepile.length

  override def toString: String = tilepile.mkString(", ")
}
