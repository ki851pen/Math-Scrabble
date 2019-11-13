package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.model.{Grid, Pile}

class Tui {
  def processInputLine(input: String, grid:Grid, pile: Pile)= {
    input match {
      case "q" => grid
      case "n" => new Grid(15)
      case "s" => pile.shuffle
      case "t" => pile.take(9)
      case _ => input.split(" ").toList match {
        case p :: equal :: plusminus :: muldiv :: blank:: digit::Nil => if(p=="p"){new Pile(equal.toInt,plusminus.toInt,muldiv.toInt,blank.toInt,digit.toInt)} else pile
        case n :: size :: Nil => if(n=="n" && size.toIntOption.getOrElse("")!=""){if(size.toInt<3)grid else new Grid(size.toInt)} else grid
        case row :: column :: value :: Nil => if(grid.isEmpty) {if(row == column && grid.size/2+1 == row.toInt)grid.set(row.toInt-1, column.toInt-1, value) else grid}
          else {grid.set(row.toInt-1, column.toInt-1, value)}
        case _ => pile
      }
    }
  }
}