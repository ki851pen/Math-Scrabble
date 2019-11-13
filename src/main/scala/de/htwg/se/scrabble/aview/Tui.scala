package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.model.{Grid, Pile, Gamefield}

class Tui {
  def processInputLine(input: String, game: Gamefield)= {
    input match {
      case "q" => game
      case "n" => Gamefield(new Grid(15), game.pile)
      case "s" => Gamefield(game.grid, game.pile.shuffle)
      case "t" => Gamefield(game.grid, game.pile.take(9))
      case _ => input.split(" ").toList match {
        case p :: equal :: plusminus :: muldiv :: blank:: digit::Nil => if(p=="p"){Gamefield(game.grid, new Pile(equal.toInt,plusminus.toInt,muldiv.toInt,blank.toInt,digit.toInt))} else game
        case n :: size :: Nil => if(n=="n" && size.toIntOption.getOrElse("")!=""){if(size.toInt<3)game else Gamefield(new Grid(size.toInt),game.pile)} else game
        case row :: column :: value :: Nil => if(game.grid.isEmpty) {if(row == column && game.grid.size/2+1 == row.toInt)Gamefield(game.grid.set(row.toInt-1, column.toInt-1, value),game.pile)else game}
          else {Gamefield(game.grid.set(row.toInt-1, column.toInt-1, value),game.pile)}
        case _ => game
      }
    }
  }
}