package de.htwg.se.scrabble

import de.htwg.se.scrabble.aview.Tui
import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.model.gameField.GameFieldFixedSizeCreateStrategy

import scala.io.StdIn.readLine
object Scrabble {
  println("This is Scrabble\n")
  val controller = new Controller(new GameFieldFixedSizeCreateStrategy())
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (!List("q", "Q", "quit", "Quit").contains(input))
  }
}
