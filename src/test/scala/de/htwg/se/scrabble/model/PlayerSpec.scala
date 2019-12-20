package de.htwg.se.scrabble.model
import de.htwg.se.scrabble.model.gameFieldComponent.gridComponent.cellComponent.cellBaseImpl.Card
import de.htwg.se.scrabble.model.gameFieldComponent.playerComponent.playerBaseImpl.Player
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = new Player("Testname")
      "have a name"  in {
        player.name should be("Testname")
      }
      "have a String representation" in {
        player.toString should be("Testname has: ")
      }
      "dont have card in hand" in {
        player.hand should be (Nil)
        player.getNrCardsInHand should be (0)
      }
    }
    "add card in hand" should {
      val player = Player("name", List(Card("=")),0)
      val moreCardPlayer = player.addToHand(List(Card("+")))
      "have more cards in hand" in {
        moreCardPlayer.getNrCardsInHand should be (2)
        moreCardPlayer.hand should be (List(Card("="),Card("+")))
      }
    }
    "use card in hand" should {
      val player = Player("name", List(Card("="), Card("="), Card("="), Card("+")),0)
      val moreCardPlayer = player.useCard(Card("="))
      "remove one instance of used card" in {
        moreCardPlayer.getNrCardsInHand should be (3)
        moreCardPlayer.hand should be (List(Card("="),Card("="),Card("+")))
      }
    }
  }
}