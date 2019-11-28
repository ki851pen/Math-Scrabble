package de.htwg.se.scrabble.controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val P1, P2, INIT, VALID, INVALID, END_GAME = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    P1 -> "A's turn",
    P2 -> "B's turn",
    INIT -> "Initialisation",
    /*VALID-> "valid equation",
    INVALID-> "invalid equation",*/
    END_GAME ->"Game ended"
  )

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }
}
