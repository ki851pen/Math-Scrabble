package de.htwg.se.scrabble.model
import de.htwg.se.scrabble.model.cell.Cell
import de.htwg.se.scrabble.model.gameField.GameField
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
      "have a String representation that include String of Grid,Pile and Playerlist" in {
        game.toString should include(game.grid.toString)
        game.toString should include(game.pile.toString)
        game.toString should include(game.playerListToString)
      }
    }
    "add a Player" should {
      val moreplayer = GameField(game.grid,game.pile,game.createPlayer("Name"))
      "have a bigger size Playerlist" in {
        moreplayer.playerList.size should be > game.playerList.size
      }
    }
    "remove a Player" should {
      val lessplayer = GameField(game.grid,game.pile,game.deletePlayer("A"))
      "have a smaller size Playerlist" in {
        lessplayer.playerList.size should be < game.playerList.size
      }
    }
    "replace a Player" should {
      val newplayer = GameField(game.grid,game.pile,game.replacePlayer("A", new Player("B")))
      "have a new Player" in {
        newplayer.playerList.keys should contain ("B")
        newplayer.playerList.values should contain (new Player("B"))
        newplayer.playerList should not be game.playerList
      }
    }
    "create with player" should {
      val playerGamefield = gameField.GameField(new Grid(5), new Pile(),Map("Poom"-> new Player("Poom"), "B"->new Player("B")))
      "have that player in playerlist(map)" in {
        playerGamefield.playerList.values should contain(Player("Poom",Nil,0))
        playerGamefield.playerList.values should contain(Player("B",Nil,0))
        playerGamefield.playerList.size should be (2)
      }
    }
  }
}
