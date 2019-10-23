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
    }
  }
}