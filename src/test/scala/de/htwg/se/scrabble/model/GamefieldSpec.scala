package de.htwg.se.scrabble.model
import org.scalatest.{Matchers, WordSpec}

class GamefieldSpec extends WordSpec with Matchers{
  "A Gamefield" when {
    val game = new Gamefield(new Grid(5), new Pile())
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
        game.toString should include(game.playerList.keys.map(key => key + ": " + game.playerList(key).getHand).mkString(", "))
      }
    }
    "add a Player" should {
      val moreplayer = Gamefield(game.grid,game.pile,game.createPlayer("Name"))
      "have a bigger size Playerlist" in {
        moreplayer.playerList.size should be > game.playerList.size
      }
    }
    "remove a Player" should {
      val lessplayer = Gamefield(game.grid,game.pile,game.deletePlayer("A"))
      "have a smaller size Playerlist" in {
        lessplayer.playerList.size should be < game.playerList.size
      }
    }
    "replace a Player" should {
      val newplayer = Gamefield(game.grid,game.pile,game.replacePlayer("A", new Player("B")))
      "have a new Player" in {
        newplayer.playerList.keys should contain ("B")
        newplayer.playerList.values should contain (new Player("B"))
        newplayer.playerList should not be game.playerList
      }
    }
  }
}
