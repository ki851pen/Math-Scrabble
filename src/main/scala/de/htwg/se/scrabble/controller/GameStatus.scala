package de.htwg.se.scrabble.controller

object GameStatus extends Enumeration{
  type GameStatus = Value
  val P1, P2, INIT, END_TURN, END_GAME, CAL = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    P1 -> "'s turn",
    P2 -> "'s turn",
    INIT -> "Initialisation",
    CAL-> "calculating score",
    END_TURN ->"Turn ended",
    END_GAME ->"Game ended"
  )

  def message(gameStatus: GameStatus): String = {
    map(gameStatus)
  }
}
