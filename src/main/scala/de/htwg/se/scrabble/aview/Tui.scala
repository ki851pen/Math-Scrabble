package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}
import de.htwg.se.scrabble.util.Observer

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  def processInputLine(input: String): Unit = {
    input match {
      case "q" | "Q" | "quit" =>
      case "next" =>
      case "g" => controller.createEmptyGrid("15")
      case "p" => controller.createPile(20,7,5,6,5)
      case "s" => controller.shufflePile()
      case "h" => println(help)
      case _ => input.split(" ").toList match {
        case g :: size :: Nil => if(g=="g") controller.createEmptyGrid(size)
        case row :: col :: value :: Nil => controller.setGrid(row,col,value)
        case _ =>
      }
      /*
      case "t" => Gamefield(game.grid, game.pile.take(9))
      case _ => input.split(" ").toList match {
        case p :: equal :: plusminus :: muldiv :: blank:: digit::Nil => if(p=="p"){Gamefield(game.grid, new Pile(equal.toInt,plusminus.toInt,muldiv.toInt,blank.toInt,digit.toInt))} else game
        case row :: column :: value :: Nil =>
        case _ => game
      }*/
    }
  }

  def help: String = """
                         || commands               |   function                                                                                                                          |
                         ||                        |                                                                                                                                     |
                         ||  g                     |   create standard size grid                                                                                                         |
                         ||                        |                                                                                                                                     |
                         ||  p                     |   create standard size pile                                                                                                         |
                         ||                        |                                                                                                                                     |
                         ||  h                     |   displays the command list                                                                                                         |
                         ||                        |                                                                                                                                     |
                         ||  q                     |   quit math-scrabble                                                                                                                |""".stripMargin


  override def update: Unit = println(controller.gameToString)
}