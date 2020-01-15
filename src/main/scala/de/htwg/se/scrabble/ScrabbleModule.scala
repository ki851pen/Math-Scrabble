package de.htwg.se.scrabble

import com.google.inject.AbstractModule
import de.htwg.se.scrabble.controller.controllerComponent._
import de.htwg.se.scrabble.model.fileIoComponent._
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.{GameFieldCreateStrategyTemplate, GameFieldFixedSizeCreateStrategy}
import de.htwg.se.scrabble.model.gridComponent._
import net.codingwell.scalaguice.ScalaModule

class ScrabbleModule extends AbstractModule with ScalaModule {
  override def configure()= {
    bind[GameFieldCreateStrategyTemplate].annotatedWithName("DefaultStrategy").toInstance(new GameFieldFixedSizeCreateStrategy())
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
    bind[CardInterface].to[gridBaseImpl.Card]

    bind[FileIOInterface].to[fileIoXmlImpl.FileIO]
  }
}
