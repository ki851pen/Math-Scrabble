package de.htwg.se.scrabble.aview

import de.htwg.se.scrabble.controller.controllerComponent.{ControllerInterface, InvalidEquation}

import scala.swing.Reactor
import scala.swing.event.Event

class Tui(controller: ControllerInterface) extends Reactor {
  listenTo(controller)

  def processInputLine(input: String): Unit = {
    val IntRegEx = "(\\d+)"
    val fixedSizes = List("3", "5", "9", "15")
    input match {
      case "q" =>
      case "init" => controller.init
      case "p" => controller.createPile(22, 8, 6, 7)
      case "s" => controller.shufflePile
      case "h" => println(help)
      case "submit" => controller.endTurn
      case "fh" => controller.fillAllHand
      case "z" => controller.undo
      case "y" => controller.redo
      case "save" => controller.save
      case "load" => controller.load
      case _ => input.split(" ").toList match {
        case command :: player :: Nil if command == "clr" => controller.clearHand(player)
        case command :: player :: Nil if command == "fh" => controller.fillHand(player)
        case command :: size :: Nil if command == "gf" => if (size.matches(IntRegEx) && fixedSizes.contains(size))
          controller.createFixedSizeGameField(size.toInt) else println("size have to be one of the following: 3, 5, 9 or 15")
        case command :: size :: equal :: plusminus :: muldiv :: digit :: Nil if command == "gf" =>
          if (List(size, equal, plusminus, muldiv, digit).forall(_.matches(IntRegEx)))
            controller.createFreeSizeGameField(size.toInt, equal.toInt, plusminus.toInt, muldiv.toInt, digit.toInt)
          else println("size have to be integer")
        case command :: equal :: plusminus :: muldiv :: digit :: Nil if command == "p" =>
          if (List(equal, plusminus, muldiv, digit).forall(_.matches(IntRegEx)))
            controller.createPile(equal.toInt, plusminus.toInt, muldiv.toInt, digit.toInt)
          else println("all characters after p have to be integer")
        case command :: row :: col :: index :: Nil if command == "set" && row.matches(IntRegEx) && col.matches(IntRegEx) && index.matches(IntRegEx) =>
          controller.setGrid(row.toInt - 1, col.toInt - 1, index.toInt)
          //if (Card(value).isValid) controller.setGrid(row.toInt - 1, col.toInt - 1, value)
          //else println("value not valid")
        case _ => println("Invalid input. Type h to get some helps.")
      }
    }
  }

  reactions += {
    case _: InvalidEquation => println("not valid equation")
    case _: Event => printTui
  }

  def printTui: Unit = {
    println(controller.gameToString)
  }

  def help: String =
    """
      || commands               |   function                                                        |
      ||                        |                                                                   |
      ||  init                  |   start a standard game                                           |
      ||                        |                                                                   |
      ||  gf [size]             |   create a grid with fixed size                                   |
      ||  gf [size] [pile]      |   create a grid with free size and pile                           |
      ||                        |                                                                   |
      ||  p                     |   create a pile of standard size                                  |
      ||                        |                                                                   |
      ||  h                     |   displays the command list                                       |
      ||                        |                                                                   |
      ||  q                     |   quit math-scrabble                                              |""".stripMargin

  def quit(): Unit = {
    println("Bye")
    System.exit(0)
  }
}
