package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.util.Observer

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  def processInputLine(input: String): Unit = {
    val IntRegEx = "(\\d+)"
    input match {
      case "q" | "Q" | "quit" =>
      case "g" => controller.createEmptyGrid(15)
      case "p" => controller.createPile(20,7,5,6,5)
      case "s" => controller.shufflePile()
      case "h" => println(help)
      //case "next" =>
      case "fh" => controller.fillAllHand()
      case _ => input.split(" ").toList match {
        case command :: name :: Nil if command == "py+" => controller.addPlayer(name)
        case command :: name :: Nil if command == "py-" => controller.removePlayer(name)
        case command :: size :: Nil if command == "g" => if(size.matches(IntRegEx)) controller.createEmptyGrid(size.toInt) else println("size have to be integer")
        case command :: player :: size :: Nil if command == "t" =>
          if(size.matches(IntRegEx))
            controller.takeFromPile(player, size.toInt) else println("size have to be integer")
        case command :: equal :: plusminus :: muldiv :: blank:: digit:: Nil if command == "p" =>
          if (List(equal, plusminus, muldiv, blank, digit).forall(_.matches(IntRegEx)))
            controller.createPile(equal.toInt, plusminus.toInt, muldiv.toInt, blank.toInt, digit.toInt)
          else println("all characters after p have to be integer")
        case row :: col :: value :: Nil if row.matches(IntRegEx) && col.matches(IntRegEx) => controller.setGrid(row,col,value)
        case _ => println("Invalid input. Type h to get some helps.")
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
