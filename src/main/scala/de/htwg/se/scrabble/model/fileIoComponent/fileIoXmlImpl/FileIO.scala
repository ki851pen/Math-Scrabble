package de.htwg.se.scrabble.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.scrabble.controller.controllerComponent.GameStatus._
import de.htwg.se.scrabble.model.fileIoComponent.FileIOInterface
import de.htwg.se.scrabble.model.gameFieldComponent.GameFieldInterface
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.Card
import de.htwg.se.scrabble.model.gridComponent.{CardInterface, GridInterface}
import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.playerComponent.PlayerInterface
import de.htwg.se.scrabble.util.Memento

import scala.xml.{NodeSeq,PrettyPrinter}

class FileIO extends FileIOInterface{
  override def load: Memento = {
    val file = scala.xml.XML.loadFile("gameField.xml")

    val statusAttr = file \\ "gamefield" \\ "@status"
    val sumAttr = file \\ "gamefield" \\ "@currentsum"
    val pilecard = file \\ "pile" \\ "card"
    //val injector  = Guice.createInjector(new ScrabbleModule)
    //val card: CardInterface = injector.getInstance(classOf[CardInterface])
    val tilepile = pilecard.map(x => Card(x.text)).toList
    val pile = Pile(tilepile)
    val grid = ???
    val playerlist = ???
    var gamefield= GameField(grid,pile,playerlist)
    val status = statusAttr.text match {
      case "init"=> Init()
      case "fc"=> FirstCard()
      case "pA"=> P("A")
      case "pB"=> P("B")
    }
    Memento(gamefield, status, sumAttr.text.toInt)
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
    <gamefield status={mem.gameStatus.toString} currentsum={mem.currentSum.toString}>
      <grid size={ gameField.grid.size.toString }>
      {
        for {
          row <- 0 until gameField.grid.size
          col <- 0 until gameField.grid.size
        } yield cellToXml(gameField.grid, row, col)
      }
    </grid>
      <pile>
        {gameField.pile.tilepile.map(cardToXml)}
      </pile>
      <playerlist>
        {gameField.playerList.map(playerToXml)}
      </playerlist>
    </gamefield>
  }


  def cardToXml(card: CardInterface) = {
    println(card)
    <card>{card.toString}</card>
  }

  def cellToXml(grid: GridInterface, row: Int, col: Int) = {
    <cell></cell>
  }

  def playerToXml(player: (String,PlayerInterface)) ={
    <player name={player._1} point={player._2.point.toString}>
      <hand>{player._2.hand.map(cardToXml)}</hand>
    </player>
  }
}
