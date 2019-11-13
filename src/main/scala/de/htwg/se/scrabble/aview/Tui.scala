package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}
import de.htwg.se.scrabble.util.Observer

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  def processInputLine(input: String): Unit = {
    val IntRegEx = "(\\d+)"
    input match {
      case "q" | "Q" | "quit" =>
      case "next" =>
      case "g" => controller.createEmptyGrid(15)
      case "p" => controller.createPile(20,7,5,6,5)
      case "s" => controller.shufflePile()
      case "h" => println(help)
      case _ => input.split(" ").toList match {
        case g :: size :: Nil if g=="g" => if(size.matches(IntRegEx)) controller.createEmptyGrid(size.toInt) else println("size have to be integer")
        case t :: size :: Nil if t=="t" => if(size.matches(IntRegEx)) controller.takeFromPile(size.toInt) else println("size have to be integer")
        case p :: equal :: plusminus :: muldiv :: blank:: digit:: Nil if p=="p" =>
          if (List(equal,plusminus,muldiv,blank,digit).forall(x =>x.matches(IntRegEx)))
          controller.createPile(equal.toInt,plusminus.toInt,muldiv.toInt,blank.toInt,digit.toInt)
          else println("all characters after p have to be integer")
        case row :: col :: value :: Nil if row.matches(IntRegEx) && col.matches(IntRegEx) => controller.setGrid(row,col,value)
        case _ => println("invalid input")
      }
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