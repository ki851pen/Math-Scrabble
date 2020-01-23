package de.htwg.se.scrabble

import com.google.inject.AbstractModule
import de.htwg.se.scrabble.controller.controllerComponent._
import de.htwg.se.scrabble.model.fileIoComponent._
import de.htwg.se.scrabble.model.gameFieldComponent._
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.{GameFieldCreateStrategyTemplate, GameFieldFixedSizeCreateStrategy}
import de.htwg.se.scrabble.model.gridComponent._
import de.htwg.se.scrabble.model.pileComponent.{PileInterface, _}
import net.codingwell.scalaguice.ScalaModule

class ScrabbleModule extends AbstractModule with ScalaModule {
  override def configure()= {
    bind[GameFieldCreateStrategyTemplate].toInstance(new GameFieldFixedSizeCreateStrategy())
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    //bind[GameFieldInterface].to[gameFieldBaseImpl.GameField]
    //bind[GridInterface].to[gridBaseImpl.Grid]
    bind[CardInterface].to[gridBaseImpl.Card]
    bind[PileInterface].to[PileBaseImpl.Pile]
    bind[FileIOInterface].to[fileIoJsonImpl.FileIO]
    //bind[FileIOInterface].to[fileIoXmlImpl.FileIO]
  }
}
