package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.controller.{Controller, GameStatus}
import de.htwg.se.scrabble.model.Card
import de.htwg.se.scrabble.util.Observer

class Tui(controller: Controller) extends Observer {
  controller.add(this)
  def processInputLine(input: String): Unit = {
    val IntRegEx = "(\\d+)"
    val fixedSizes = List("3", "5", "9", "15")
    input match {
      case "q" | "Q" | "quit" => controller.quit()
      case "init" => controller.init()
      case "p" => controller.createPile(20,7,5,6,5)
      case "s" => controller.shufflePile()
      case "h" => println(help)
      case "submit" => controller.endTurn()
      case "fh" => controller.fillAllHand()
        //case "giveup"
      case _ => input.split(" ").toList match {
        case command :: player :: Nil if command == "clr" =>controller.clearHand(player)
        case command :: player :: Nil if command == "fh" =>controller.fillHand(player)
        case command :: name :: Nil if command == "py+" => controller.addPlayer(name)
        case command :: name :: Nil if command == "py-" => controller.removePlayer(name)
        case command :: size :: Nil if command == "gf" => if (size.matches(IntRegEx) && fixedSizes.contains(size))
          controller.createFixedSizeGameField(size.toInt) else println("size have to be one of the following: 3, 5, 9 or 15")
        case command :: size :: equal :: plusminus :: muldiv :: blank:: digit:: Nil if command == "gf" =>
          if (List(size, equal, plusminus, muldiv, blank, digit).forall(_.matches(IntRegEx)))
            controller.createFreeSizeGameField(size.toInt, equal.toInt, plusminus.toInt, muldiv.toInt, blank.toInt, digit.toInt)
          else println("size have to be integer")
        case command :: equal :: plusminus :: muldiv :: blank:: digit:: Nil if command == "p" =>
          if (List(equal, plusminus, muldiv, blank, digit).forall(_.matches(IntRegEx)))
            controller.createPile(equal.toInt, plusminus.toInt, muldiv.toInt, blank.toInt, digit.toInt)
          else println("all characters after p have to be integer")
        case command :: row :: col :: value :: Nil if command == "set" && row.matches(IntRegEx) && col.matches(IntRegEx) =>
          if (Card(value).isValid) controller.setGrid(row,col,value)
          else println("value not valid")
        case _ => println("Invalid input. Type h to get some helps.")
      }
    }
  }

  def help: String = """
                         || commands               |   function                                                        |
                         ||                        |                                                                   |
                         ||  g                     |   create standard size grid                                       |
                         ||  gf [size]             |   create a grid with fixed size                                   |
                         ||  gf [size] [pile]      |   create a grid with free size and pile                           |
                         ||                        |                                                                   |
                         ||  p                     |   create standard size pile                                       |
                         ||                        |                                                                   |
                         ||  h                     |   displays the command list                                       |
                         ||                        |                                                                   |
                         ||  q                     |   quit math-scrabble                                              |""".stripMargin

  override def update: Boolean = {
    println(controller.gameToString)
    //println(GameStatus.message(controller))
    true
  }
}
