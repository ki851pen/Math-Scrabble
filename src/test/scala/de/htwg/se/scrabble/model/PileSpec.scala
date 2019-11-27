package de.htwg.se.scrabble.model
import org.scalatest.{Matchers, WordSpec}

class PileSpec extends WordSpec with Matchers {
  "A Pile" when {
    "create by defaulft" should{
      val newpile = new Pile()
      "have a list" in {
        newpile.tilepile shouldBe a [List[_]]
      }
      "have length of 100" in{
        newpile.size should be (100)
      }
      "have a String representation" in{
        newpile.toString should be ("=, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, +, +, +, +, +, +, +, -, -, -, -, -, -, -, *, *, *, *, *, /, /, /, /, /, ?, ?, ?, ?, ?, ?, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9")
      }
    }
    "shuffle" should{
      val pile = new Pile()
      "have same length" in {
        pile.shuffle.tilepile.length should be (100)
      }
      "have a different String representation" in {
        pile.shuffle.toString should not be pile.toString
      }
    }
    "drop some Card off" should {
      val pile = new Pile()
      val newPile = pile.drop(10)
      "have a size reduced" in{
        newPile.size should be(pile.size - 10)
      }
    }
    "when take cards" should {
      val takenpile = new Pile()
      val cardsFromPile = takenpile.take(5)
      "taken cards should be cards at the start of the pile" in{
        cardsFromPile.size should be(5)
        cardsFromPile should be (takenpile.tilepile.take(5))
      }
    }
    "when add cards" should {
      val pile = new Pile()
      "have increase size if value is valid" in{
        val addedPile = pile.add("3",5)
        addedPile.size should be (pile.size + 5)
      }
      "stay the same if value is not valid" in{
        val addedPile = pile.add("n",5)
        addedPile.size should be (pile.size)
      }
    }
  }
}