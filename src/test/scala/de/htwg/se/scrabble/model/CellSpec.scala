package de.htwg.se.scrabble.model

import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.scrabble.util.SpecialCell._
class CellSpec extends WordSpec with Matchers {

  "A Cell" when {
    "not set to any value " should {
      val emptyCell = new Cell("")
      "have value \"\"" in {
        emptyCell.value should be ("")
      }
      "not be set" in {
        emptyCell.isSet should be (false)
      }
    }
    "set to a specific value" should {
      val nonEmptyCell = new Cell("5")
      "return that value" in {
        nonEmptyCell.value should be("5")
      }
      "be set" in {
        nonEmptyCell.isSet should be(true)
      }
    }
    "special" should{
      val specialCell = Cell("1", Triple)
      "be not normal" in {
        specialCell.isNormal should be(false)
      }
    }
  }
}
