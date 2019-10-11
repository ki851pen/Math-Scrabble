import scala.util.Random
val point = Map("a"->1, "b"->3, "c"->3, "d"->2, "e"->1, "f"->4
  , "g"->2, "h"->4, "i"->1, "j"->8, "k"->5, "l"->1, "m"->3, "n"->1
  , "o"->1, "p"->3, "q"->10, "r"->1, "s"->1, "t"->1, "u"->1, "v"->4
  , "w"->4, "x"->8, "y"->4, "z"->10)

point.keys
point.values
point.isEmpty
point.size

point("a")
point("q")
point("")

val alpha = 'a' to 'z'

val hand1 = (1 to 7).map(_ => Random.nextInt(26))
val hand2 = Seq.fill(7)(alpha(Random.nextInt(26)))
hand1.concat(hand2)

val List1 = List.empty[String]
val List2 = List1 :+ "a":+"b"
val List3 = List2.filter(_!="b")

def remove(alp: String, list: List[String]) = list diff List(alp)
remove("b",List2)

val list = ('a' to 'g').toList

val union = alpha.concat(list)
val inter = alpha.intersect(list)
val dif = alpha.diff(list)

val i = 12


