package de.htwg.se.scrabble

import com.google.inject.AbstractModule
import de.htwg.se.scrabble.controller.controllerComponent._
import net.codingwell.scalaguice.ScalaModule

class ScrabbleModule extends AbstractModule with ScalaModule {
  override def configure()= {
    bind[ControllerInterface].to[controllerBaseImpl.Controller]
  }
}
