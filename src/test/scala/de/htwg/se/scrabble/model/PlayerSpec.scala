package de.htwg.se.scrabble.model
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = new Player("Testname")
      "have a name"  in {
        player.name should be("Testname")
      }
      "have a nice String representation" in {
        player.toString should be("Testname")
      }
      "dont have card in hand" in {
        player.getHand should be (Nil)
        player.getNrCardsInHand should be (0)
      }
    }
    "add card in hand" should {
      val player = Player("name", List(Card("=")))
      val moreCardPlayer = player.addToHand(List(Card("+")))
      "have more cards in hand" in {
        moreCardPlayer.getNrCardsInHand should be (2)
        moreCardPlayer.getHand should be (List(Card("="),Card("+")))
      }
    }
    "use card in hand" should {
      val player = Player("name", List(Card("="), Card("="), Card("="), Card("+")))
      val moreCardPlayer = player.useCard(Card("="))
      "remove one instance of used card" in {
        moreCardPlayer.getNrCardsInHand should be (3)
        moreCardPlayer.getHand should be (List(Card("="),Card("="),Card("+")))
      }
    }
  }
}