package de.htwg.se.scrabble.model
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl
import de.htwg.se.scrabble.model.gameFieldComponent.gameFieldBaseImpl.GameField
import de.htwg.se.scrabble.model.gridComponent.gridBaseImpl.{Card, Cell, Grid}
import de.htwg.se.scrabble.model.pileComponent.PileBaseImpl.Pile
import de.htwg.se.scrabble.model.playerComponent.playerBaseImpl.Player
import org.scalatest.{Matchers, WordSpec}

class GameFieldSpec extends WordSpec with Matchers{
  "A Gamefield" when {
    val game = new GameField(new Grid(5), new Pile())
    "create" should {
      "have automatically have a Player in Playerlist" in {
        game.playerList.isEmpty should be(false)
      }
      "can access grid and pile" in {
        game.grid shouldBe a[Grid]
        game.grid.cell(3, 3) shouldBe a[Cell]
        game.pile shouldBe a[Pile]
        game.pile.tilepile shouldBe a[List[_]]
      }
      "have a String representation that include String of Grid, Pile and Playerlist" in {
        game.toString should include(game.grid.toString)
        //game.toString should include(game.pile.toString)
        game.toString should include(game.playerListToString)
      }
    }
    /*"rename a Player" should {
      val gameWithNewPlayerName = game.renamePlayer("A", "B")
      "have a new Player" in {
        gameWithNewPlayerName.playerList.keys should contain ("B")
        gameWithNewPlayerName.playerList.values should contain (new Player("B"))
        gameWithNewPlayerName.playerList should not be game.playerList
      }
    }*/
    "create with player" should {
      val playerGamefield = gameFieldBaseImpl.GameField(new Grid(5), new Pile(),Map("Poom"-> new Player("Poom",List(Card("0"),Card("=")),0), "B"->new Player("B")))
      "have that player in playerlist(map)" in {
        playerGamefield.playerList.values should contain(Player("Poom",List(Card("0"),Card("=")),0))
        playerGamefield.playerList.values should contain(Player("B",Nil,0))
        playerGamefield.playerList.size should be (2)
      }
      "have ability to play card on player hand" in {
        val newergame = playerGamefield.playerPlay("Poom",2,2,0)
        newergame.playerList.values should contain(Player("Poom",List(Card("=")),0))
      }
      "have ability to clear player hand" in {
        val newergame = playerGamefield.clearHand("Poom")
        newergame.playerList.values should contain(Player("Poom",Nil,0))
      }
    }
  }
}
