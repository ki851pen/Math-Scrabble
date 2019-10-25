package de.htwg.se.scrabble.model
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Testname")
      "have a name"  in {
        player.name should be("Testname")
      }
      "have a nice String representation" in {
        player.toString should be("Testname")
      }
    }
  }
}