package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller.Controller
import de.htwg.se.scrabble.controller.GameStatus.{FirstCard, P}

import scala.swing.{Button, Dimension, FlowPanel, Label, Panel}
import scala.swing.event.ButtonClicked

class InfoPanel(controller: Controller) extends FlowPanel {
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
    text = "Player A: " + controller.gameFacade.playerList("A").point.toString
  }
  contents += PointA
  val PointB = new Label {
    text = "Player B: " + controller.gameFacade.playerList("B").point.toString
  }
  contents += PointB
  val stackL = new Label {
    text = "cards in stack: " + controller.gameFacade.pile.size.toString
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
    reactions += { case _: ButtonClicked => controller.setGrid(controller.guirow, controller.guicol, controller.guival) }
  }
  contents += new Button() {
    text = "submit"
    preferredSize = full
    reactions += { case _: ButtonClicked => controller.endTurn() }
  }
  visible = true

  def redraw: Unit = {
    stackL.text = "cards in stack: " + controller.gameFacade.pile.size.toString
    PointA.text = "Player A: " + controller.gameFacade.playerList("A").point.toString
    PointB.text = "Player B: " + controller.gameFacade.playerList("B").point.toString
    rowClicked.text = "clicked row: " + (controller.guirow + 1)
    colClicked.text = "clicked col: " + (controller.guicol + 1)
    repaint
  }
}
