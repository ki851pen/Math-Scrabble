package de.htwg.se.scrabble.util

import de.htwg.se.scrabble.controller.GameStatus.State
import de.htwg.se.scrabble.model.gameField.GameField

case class Memento(gameField: GameField, gameStatus: State, currentSum: Int)
