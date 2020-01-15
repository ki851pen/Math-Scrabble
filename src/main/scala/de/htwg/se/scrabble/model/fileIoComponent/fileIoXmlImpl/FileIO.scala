package de.htwg.se.scrabble.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.scrabble.controller.controllerComponent.ControllerInterface
import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gridComponent.{CardInterface, GridInterface}
import de.htwg.se.scrabble.model.playerComponent.PlayerInterface
import de.htwg.se.scrabble.util.Memento

import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface{
  // use set state(gamefield, sum, state) when finished load in controller
  override def load: Memento = {
    var gamefield: GameFieldInterface = null
    val file = scala.xml.XML.loadFile("gameField.xml")
    val sizeAttr = file \\ "grid" \"@size"
    val size = sizeAttr.text.toInt

  }

  override def save(mem: Memento): Unit = saveString(mem)

  def saveString(mem: Memento): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("gameField.xml"))
    val prettyPrinter = new PrettyPrinter(120,4)
    val xml = prettyPrinter.format(gameFieldToXml(mem))
    pw.write(xml)
    pw.close()
  }

  def gameFieldToXml(mem: Memento) = {
    val gameField = mem.gameField
    <gamefield status={mem.gameStatus} currentsum = {mem.currentSum}>
      <grid>
        <grid size={ gameField.grid.size.toString }>
          {
          for {
            row <- 0 until gameField.grid.size
            col <- 0 until gameField.grid.size
          } yield cellToXml(gameField.grid, row, col)
          }
        </grid>
      </grid>
      <pile>
        {gameField.pile.tilepile.foreach(cardToXml)}
      </pile>
      <playerlist>
        {gameField.playerList.foreach(playerToXml)}
      </playerlist>
    </gamefield>
  }

  def cardToXml(card: CardInterface) = {
    <card>{card.toString}</card>
  }

  def cellToXml(grid: GridInterface, row: Int, col: Int) = {
    <cell></cell>
  }

  def playerToXml(player: (String,PlayerInterface)) ={
    <player name={player._1} point={player._2.point}>
      <hand>{player._2.hand.foreach(cardToXml)}</hand>
    </player>
  }
}
