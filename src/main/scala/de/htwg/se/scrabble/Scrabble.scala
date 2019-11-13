package de.htwg.se.scrabble

import de.htwg.se.scrabble.aview.Tui
import de.htwg.se.scrabble.model.{Grid, Pile, Gamefield}

import scala.io.StdIn.readLine
object Scrabble {
  def main(args: Array[String]): Unit = {
    println("This is Scrabble")
    var input: String = ""
    var grid = new Grid(15)
    var pile = new Pile()
    var game = Gamefield(grid, pile)
    val tui = new Tui()
    do {
      println(game)
      input = readLine()
      game = tui.processInputLine(input, game)
    } while (input != "q")
  }
}
