package de.htwg.se.scrabble

import de.htwg.se.scrabble.aview.Tui
import de.htwg.se.scrabble.model.{Grid, Pile}

import scala.io.StdIn.readLine
object Scrabble {


  def main(args: Array[String]): Unit = {
    println("Hello World")
    println("This is Scrabble")
    var input: String = ""
    var grid = new Grid(9)
    var pile = Pile()
    val tui = new Tui
    do {
      println("Grid : \n " + grid)
      println("Pile : " + pile)
      input = readLine()
      grid = tui.processInputLine(input, grid)
    } while (input != "q")
  }
}
