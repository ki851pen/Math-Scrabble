package de.htwg.se.scrabble.model

import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.scrabble.util.SpecialCell._
class CardSpec extends WordSpec with Matchers {
  "A Card" should {
    val Card = new Card("")
    "have a valid set that contain number as String" in {
      Card.validSet.contains("0") should be(true)
    }
    "and contain arithmetic operation as String" in {
      Card.validSet.contains("=") should be(true)
    }
  }
  "A Card" when{
    "set to 1 digit number" should{
      val numbercard = Card("7")
      "be valid" in{
        numbercard.isValid should be(true)
      }
      "have a parse value be Integer" in {
        numbercard.parseValue shouldBe a [Int]
      }
      "have a String representation" in {
        numbercard.toString should be("7")
      }
    }
    "set to 2 digit number" should{
      val numbercard = Card("27")
      "not be valid" in{
        numbercard.isValid should be(false)
      }
      "have a parse value be blank" in {
        numbercard.parseValue should be ("")
      }
      "have a String representation" in {
        numbercard.toString should be("")
      }
    }
    "set to alphabet" should{
      val numbercard = Card("m")
      "not be valid" in{
        numbercard.isValid should be(false)
      }
      "have a parse value be blank" in {
        numbercard.parseValue should be ("")
      }
      "have a String representation" in {
        numbercard.toString should be("")
      }
    }
    "set to arithmetic operation" should{
      val numbercard = Card("*")
      "not be valid" in{
        numbercard.isValid should be(true)
      }
      "have a parse value be arithmetic operation" in {
        numbercard.parseValue should be ("*")
      }
      "have a String representation" in {
        numbercard.toString should be("*")
      }
    }
    "valid" should {
      val validCard = Card("=")
      val secondvalidCard = Card("7")
      "have a point assign to them" in {
        validCard.getpoint should be (1)
        secondvalidCard.getpoint should be (4)
      }
    }
  }
}


