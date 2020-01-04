package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.{FirstCard, P}
import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface

import scala.swing.{Button, Dimension, FlowPanel, Label, Panel}
import scala.swing.event.ButtonClicked

class InfoPanel(controller: ControllerInterface) extends FlowPanel {
  preferredSize = new Dimension(200, 500)
  val full = new Dimension(150, 40)
  val half = new Dimension(74, 40)

  class FreeSpace extends Panel {
    preferredSize = full
  }

  contents += new FreeSpace
  contents += new Label {
    preferredSize = full
    text = "score"
  }

  val PointA = new Label {
    text = "Player A: " + controller.getGameField.playerList("A").point
  }
  contents += PointA
  val PointB = new Label {
    text = "Player B: " + controller.getGameField.playerList("B").point
  }
  contents += PointB
  val stackL = new Label {
    text = "cards in stack: " + controller.getGameField.pile.size
  }
  contents += stackL
  contents += new FreeSpace
  val rowClicked = new Label {
    text = "clicked row: 0"
  }
  contents += rowClicked
  val colClicked = new Label {
    text = "clicked col: 0"
  }
  contents += colClicked
  contents += new FreeSpace

  contents += new Button() {
    text = "switch cards"
    preferredSize = full
    reactions += { case _: ButtonClicked => controller.gameStatus match {
      case P(player) => controller.changeHand(player)
      case FirstCard() => controller.changeHand("A")
    }
    }
  }
  contents += new Button() {
    text = "set"
    preferredSize = full
    reactions += { case _:
      ButtonClicked =>
      controller.putCardInCell
    }
  }
  contents += new Button() {
    text = "submit"
    preferredSize = full
    reactions += { case _: ButtonClicked => controller.endTurn }
  }
  visible = true

  def redraw: Unit = {
    stackL.text = "cards in stack: " + controller.getGameField.pile.size.toString
    PointA.text = "Player A: " + controller.getGameField.playerList("A").point.toString
    PointB.text = "Player B: " + controller.getGameField.playerList("B").point.toString
    rowClicked.text = "clicked row: " + (controller.currentSelectedRow + 1)
    colClicked.text = "clicked col: " + (controller.currentSelectedCol + 1)
    repaint
  }
}
