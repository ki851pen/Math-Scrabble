package de.htwg.se.scrabble.controller

import scala.swing.event.Event

case class GameFieldChanged() extends Event
case class GridSizeChanged() extends Event
case class CardsChanged() extends Event
case class ClickChanged() extends Event