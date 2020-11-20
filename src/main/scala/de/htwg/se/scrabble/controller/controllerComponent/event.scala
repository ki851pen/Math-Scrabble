package de.htwg.se.scrabble.controller.controllerComponent

import scala.swing.event.Event

case class GameFieldChanged() extends Event
case class GridSizeChanged() extends Event
case class CardsChanged() extends Event
case class ClickChanged() extends Event
case class changeSize() extends Event
case class InvalidEquation() extends Event
case class ButtonSet(row: Int, col: Int) extends Event