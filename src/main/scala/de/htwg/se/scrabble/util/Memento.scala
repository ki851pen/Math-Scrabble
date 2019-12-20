package de.htwg.se.scrabble.util

import de.htwg.se.scrabble.controller.GameStatus.State
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameField

case class Memento(gameField: GameField, gameStatus: State, currentSum: Int)
