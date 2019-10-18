package de.htwg.se.scrabble.model

case class Matrix[T] (rows:Vector[Vector[T]]){
  def this(size : Int, cell : T) = this(Vector.tabulate(size,size){(row,col) => cell})
  val size:Int = rows.size

}
