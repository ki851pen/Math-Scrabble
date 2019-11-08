package de.htwg.se.scrabble

import de.htwg.se.scrabble.aview.Tui
import de.htwg.se.scrabble.model.{Grid, Pile}

import scala.io.StdIn.readLine
object Scrabble {
  def main(args: Array[String]): Unit = {
    println("This is Scrabble")
    var input: String = ""
    var grid = new Grid(15)
    var pile = new Pile()
    val tui = new Tui()
    do {
      println("Grid : " + grid)
      println("Pile : " + pile)
      input = readLine()
      //grid = tui.processInputLine(input, grid, pile)
      val gridOrPile = tui.processInputLine(input, grid, pile)
      gridOrPile match {
        case instanceOfPile: Pile => pile = instanceOfPile
        case instanceOfGrid: Grid => grid = instanceOfGrid
      }
    } while (input != "q")
  }
}
