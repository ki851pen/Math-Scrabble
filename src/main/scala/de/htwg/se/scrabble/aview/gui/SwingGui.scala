package de.htwg.se.scrabble.aview.gui

import de.htwg.se.scrabble.controller._

import scala.swing.Swing.LineBorder
import scala.swing._

class SwingGui(controller: Controller) extends MainFrame {
  listenTo(controller)
  title = "Math Scrabble"
  size = new Dimension(300, 200)

  def gridPanel = new gridPanel(controller)

  contents = new BorderPanel {
    add(gridPanel, BorderPanel.Position.West)
  }

  menuBar = new MenuBar{
    contents += new Menu("File") {
      contents += new MenuItem(Action("New Game") {controller.init()})
      contents += new MenuItem(Action("Quit") { System.exit(0) })
    }
    contents += new Menu("Edit") {
      contents += new MenuItem(Action("Undo") { controller.undo() })
      contents += new MenuItem(Action("Redo") { controller.redo() })
    }
  }

  visible = true
  redraw

  def redraw = {
    repaint
  }

  reactions += {
    case event: GridChanged => gridPanel.paintField
    case event: StatusChanged => redraw
    case event: PileChanged => redraw
    case event: CardsChanged => redraw
  }
}




