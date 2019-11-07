package de.htwg.se.scrabble.model

import org.scalatest.{Matchers, WordSpec}
import de.htwg.se.scrabble.util.SpecialCell._
class CardSpec extends WordSpec with Matchers {
  "A Card" when {

  }
}/*
      "set to 3" should {
        val validcell = new Cell("3")
        "have value string 3" in {
          validcell.value should be("3")
        }
        "parse string 3 to int" in {
          validcell.parseValue should be(3)
        }
        "is valid" in{
          validcell.isValid should be(true)
        }
      }
      "set to +" should {
        val pluscell = new Cell("+")
        "have value +" in {
          pluscell.value should be("+")
        }
        "have the same parsevulue as value" in {
          pluscell.parseValue should be("+")
        }
      "is valid" in{
        pluscell.isValid should be(true)
      }
    }
    "set to n" should {
      val notvalidcell = new Cell("n")
      "have value n" in {
        notvalidcell.value should be("n")
      }
      "have empty string as parsevulue" in {
        notvalidcell.parseValue should be("")
      }
      "is not valid" in{
        notvalidcell.isValid should be(false)
      }
    }
  }*/
    /*

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
  }*/


