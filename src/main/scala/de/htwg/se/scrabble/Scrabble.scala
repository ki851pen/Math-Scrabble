package de.htwg.se.scrabble

import de.htwg.se.scrabble.aview.Tui
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.{Gamefield, Grid, Pile}

import scala.io.StdIn.readLine
object Scrabble {
  val controller = new Controller(Gamefield(new Grid(15), new Pile()))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    println("This is Scrabble")
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
