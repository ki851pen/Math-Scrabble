package de.htwg.se.scrabble.controller

object GameStatus extends Enumeration {
  type GameStatus = Value
  val IDLE, ILLEGAL, LEGAL, GAME_OVER = Value

  val map: Map[GameStatus, String] = Map[GameStatus, String](
    IDLE -> "",
    ILLEGAL -> "illegal Word",
    LEGAL -> "legal Word",
    GAME_OVER -> "\nGAME OVER -> Press 'new' for new game"
  )

  def message(gameStatus: GameStatus): String = map(gameStatus)
}

