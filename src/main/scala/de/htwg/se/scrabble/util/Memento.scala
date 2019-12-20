package de.htwg.se.scrabble.util

import de.htwg.se.scrabble.controller.GameStatus.State
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface

case class Memento(gameField: GameFieldInterface, gameStatus: State, currentSum: Int)
