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
      val othernonEmptyCell = Cell("5", Double)

      "return that value" in {
        nonEmptyCell.value should be("5")
        othernonEmptyCell.value should be("5")
      }
      "be set" in {
        nonEmptyCell.isSet should be(true)
        othernonEmptyCell.isSet should be(true)
      }
    }
    "special" should{
      val specialCell = Cell("1", Triple)
      val normalCell = Cell("1", Normal)
      "be not normal" in {
        specialCell.isNormal should be(false)
        normalCell.isNormal should be(true)
      }
      "set to 3" should {
        val validcell = new Cell("3")
        "have value string 3" in {
          validcell.value should be("3")
        }
        "parse string 3 to int" in {
          validcell.parsevalue should be(3)
        }
        "is valid" in{
          validcell.isvalid should be(true)
        }
      }
      "set to +" should {
        val pluscell = new Cell("+")
        "have value +" in {
          pluscell.value should be("+")
        }
        "have the same parsevulue as value" in {
          pluscell.parsevalue should be("+")
        }
      "is valid" in{
        pluscell.isvalid should be(true)
      }
    }
    "set to n" should {
      val notvalidcell = new Cell("n")
      "have value n" in {
        notvalidcell.value should be("n")
      }
      "have empty string as parsevulue" in {
        notvalidcell.parsevalue should be("")
      }
      "is not valid" in{
        notvalidcell.isvalid should be(false)
      }
    }
  }
  "A Cell" should {
    "have a valid list that contain number as String" in {
      val testCell = new Cell("")
      testCell.validlist.contains("0") should be(true)
    }
    "and contain arithmetic operation as String" in {
      val testCell = new Cell("")
      testCell.validlist.contains("=") should be(true)
    }
    }
  }
}
