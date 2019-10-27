package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.model.{Grid,Pile}

class Tui {

  def processInputLine(input: String, pile: Pile) = {
    input match {
      case "s" => pile.shuffle
    }
  }

}
