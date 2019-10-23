package de.htwg.se.scrabble.model
import org.scalatest.{Matchers, WordSpec}

class PileSpec extends WordSpec with Matchers {
  "A Pile" when {
    "create by defaulft" should{
      val newpile = Pile()
      "have a list" in {
        newpile.tilepile.isInstanceOf[List[_]] should be (true)
      }
      "have length of 100" in{
        newpile.size should be (100)
      }
      "have a String representation" in{
        newpile.toString should be ("List(=, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, =, +, +, +, +, +, +, +, -, -, -, -, -, -, -, *, *, *, *, *, /, /, /, /, /, _, _, _, _, _, _, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9)")
      }
    }
  }
}