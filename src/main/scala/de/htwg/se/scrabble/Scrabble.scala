package de.htwg.se.scrabble

import de.htwg.se.scrabble.aview.Tui
import de.htwg.se.scrabble.aview.gui.SwingGui
import de.htwg.se.scrabble.controller._
import de.htwg.se.scrabble.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameFieldFixedSizeCreateStrategy

import scala.io.StdIn.readLine

object Scrabble {
  println("This is Scrabble")
  val controller = new Controller(new GameFieldFixedSizeCreateStrategy())
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.publish(new GameFieldChanged)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
