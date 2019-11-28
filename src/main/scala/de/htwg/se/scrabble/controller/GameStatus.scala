package de.htwg.se.scrabble.controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val P1, P2, INIT, VALID, INVALID, END_TURN, END_GAME = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    P1 -> "'s turn",
    P2 -> "'s turn",
    INIT -> "Initialisation",
    VALID-> "valid equation",
    INVALID-> "invalid equation",
    END_TURN ->"Turn ended",
    END_GAME ->"Game ended"
  )

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }
}
