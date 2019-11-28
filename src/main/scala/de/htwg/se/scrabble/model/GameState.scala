package de.htwg.se.scrabble.model
/*
 *
 * This is only for test
 * To learn how state Pattern work
 *
 *
 */
trait GameState {
  def displayState()
  def changeState()
}

class SetupState(game: Game, context: TestContext) extends GameState {
  override def displayState(): Unit = context.on()

  override def changeState(): Unit = {
    context.off()
    game.currentState = game.playState
  }
}

class PlayState(game: Game,context: TestContext) extends GameState {
  override def displayState(): Unit = context.on()

  override def changeState(): Unit = {
    context.off()
    game.currentState = game.endState
  }
}

class EndState(game: Game,context: TestContext) extends GameState {
  override def displayState(): Unit = context.on()

  override def changeState(): Unit = {
    context.off()
  }
}

trait TestContext{
  def on()
  def off()
}

class setupContext extends TestContext{
  def on(): Unit = println("start setup")
  def off(): Unit = println("end setup")
}

class playContext extends TestContext{
  def on(): Unit = println("start play")
  def off(): Unit = println("end play")
}

class endContext extends TestContext{
  def on(): Unit = println("start end")
  def off(): Unit = {
    println("game end")
    System.exit(0)
  }
}

class Game {
  var initialContext: TestContext = new setupContext()
  var setupState: GameState = new SetupState(this, initialContext)
  var playState: GameState = new PlayState(this, new playContext)
  var endState: GameState = new EndState(this, new endContext)
  var currentState: GameState = new SetupState(this, initialContext)

  def changeState(): Unit ={
    currentState.changeState()
  }
  def displayState(): Unit ={
    currentState.displayState()
  }
}

object Test extends App{
  val game: Game = new Game
  while(true) {
    game.displayState()
    if(game.currentState.isInstanceOf[PlayState]) {
      Thread.sleep(1000)
      game.changeState()
    } else {
      Thread.sleep(5000)
      game.changeState()
    }
  }
}
