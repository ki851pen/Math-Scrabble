package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.model.{Grid, Pile}

class Tui {
  def processInputLine(input: String, grid:Grid, pile: Pile) = {
    input match {
      //case "q" => grid
      //case "n" => new Grid(9)
      case "s" => pile.shuffle
      case "t" => pile.take(9)
      case _ => pile
    }
  }
}