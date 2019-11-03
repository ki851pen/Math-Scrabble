package de.htwg.se.scrabble.model
import org.scalatest.{Matchers, WordSpec}

class PileSpec extends WordSpec with Matchers {
  "A Pile" when {
    "create by defaulft" should{
      val newpile = Pile()
      "have a list" in {
        newpile.tilepile shouldBe a [List[_]]
      }
      "have length of 100" in{
        newpile.size should be (100)
      }
      "have a String representation" in{
        newpile.toString should be ("List(=, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, +, +, +, +, +, +, +, -, -, -, -, -, -, -, *, *, *, *, *, /, /, /, /, /, _, _, _, _, _, _, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9)")
      }
    }
    "shuffle" should{
      val pile = Pile()
      "have same length" in {
        pile.shuffle.tilepile.length should be (100)
      }
      "have a different String representation" in {
        pile.shuffle.toString should not be ("")
      }
    }
  }
}