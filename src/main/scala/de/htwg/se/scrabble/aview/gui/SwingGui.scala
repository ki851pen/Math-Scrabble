package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller.controllerComponent.GameStatus.{FirstCard, Init, P}
import de.htwg.se.scrabble.controller._
import de.htwg.se.scrabble.controller.controllerComponent.{CardsChanged, ClickChanged, ControllerInterface, GameFieldChanged, GridSizeChanged}
import de.htwg.se.scrabble.util.CustomColors

import scala.swing._
import scala.swing.event.ButtonClicked

class SwingGui(controller: ControllerInterface) extends MainFrame {
  listenTo(controller)
  title = "Math Scrabble"

  var gridPanel = new MyGridPanel(controller)
  var infoPanel = new InfoPanel(controller)
  var handPanel: FlowPanel = new FlowPanel {
    visible = true
  }
  listenTo(handPanel)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.Center)
    add(infoPanel, BorderPanel.Position.East)
    add(handPanel, BorderPanel.Position.South)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem(Action("New Game") {
        controller.init
      })
      contents += new MenuItem(Action("Save") {
        controller.save
      })
      contents += new MenuItem(Action("Load") {
        controller.load
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    contents += new Menu("Edit") {
      contents += new MenuItem(Action("Undo") {
        controller.undo
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
      })
    }
    contents += new Menu("Game size") {
      contents += new MenuItem(Action("3x3") {
        controller.createFixedSizeGameField(3); controller.fillAllHand
      })
      contents += new MenuItem(Action("5x5") {
        controller.createFixedSizeGameField(5); controller.fillAllHand
      })
      contents += new MenuItem(Action("9x9") {
        controller.createFixedSizeGameField(9); controller.fillAllHand
      })
      contents += new MenuItem(Action("15x15") {
        controller.createFixedSizeGameField(15); controller.fillAllHand
      })
    }
  }

  visible = true
  redraw

  def resize = {
    val NewGridPanel = new MyGridPanel(controller)
    contents = new BorderPanel {
      add(NewGridPanel, BorderPanel.Position.Center)
      add(infoPanel, BorderPanel.Position.East)
      add(handPanel, BorderPanel.Position.South)
    }
  }

  def redrawWithoutGrid: Unit = {
    var p = "A"
    controller.gameStatus match {
      case P(player) => p = player
      case FirstCard() | Init() => p = "A"
    }
    handPanel.contents.clear()
    handPanel.contents += new Label("Status: " + controller.gameStatus)
    handPanel.contents += new Label(" Your hand: ")
    val currentHand = controller.getGameField.playerList(p).hand
    currentHand.foreach(card => {
      val button = new Button {
        background = CustomColors.Blue
        preferredSize = new Dimension(45, 45)
        text = card.toString
      }
      handPanel.contents += button
      listenTo(button)
      reactions += {
        case ButtonClicked(b) if b == button =>
          b.background = CustomColors.Green
          controller.chooseCardInHand(currentHand.indexOf(card))
          printf("Card: %s with index %d\n", card.toString, currentHand.indexOf(card))
      }
    })
    infoPanel.redraw
    handPanel.revalidate()
    repaint
  }

  def redraw = {
    gridPanel.redraw
    redrawWithoutGrid
  }

  reactions += {
    case _: GameFieldChanged => redraw
    case _: GridSizeChanged => resize
    case _: CardsChanged => redrawWithoutGrid
    case _: ClickChanged => redrawWithoutGrid
  }
}