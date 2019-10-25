package de.htwg.se.scrabble.model

case class Mathtile() {
  val point: Map[Any, Int] = Map("="->1, "+"->1, "-"->1, "*"->2, "/"->3, "_"->0
    , 1->1, 2->1, 3->2, 4->2, 5->3, 6->2, 7->4, 8->2, 9->2, 0-> 1)
}


