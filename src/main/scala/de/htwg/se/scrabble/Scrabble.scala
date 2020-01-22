package de.htwg.se.scrabble

import com.google.inject.Guice
import de.htwg.se.scrabble.aview.Tui
import de.htwg.se.scrabble.aview.gui.SwingGui
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface

import scala.io.StdIn.readLine

object Scrabble {
  val injector = Guice.createInjector(new ScrabbleModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.init

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
