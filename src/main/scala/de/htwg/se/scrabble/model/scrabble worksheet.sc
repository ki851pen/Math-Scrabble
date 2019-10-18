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
//point("")

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

val x1 = List.fill(20)("=")
val x2 = List.fill(7)("+")
val x3 = List.fill(7)("-")
val x4 = List.fill(5)("*")
val x5 = List.fill(5)("/")
val x6 = List.fill(4)("_")
val x7 = List.fill(5)((0 to 9).toList).flatten
val x = x1 ::: x2 ::: x3 ::: x4 ::: x5 :::x6:::x7
x.length
val sx = Random.shuffle(Random.shuffle(Random.shuffle(x)))
val hand = sx.take(9)
val nx = sx.drop(9)
val snx = Random.shuffle(Random.shuffle(Random.shuffle(nx)))
snx.take(9)
snx.drop(9)

9*1==9

val in = "9*1=9=4+5"
val in2 = "33+67"

in.contains('=')
val sp = in.split('=')

//val check = x => _.size > 1

in2.split('=')
val ina = in.toCharArray

if(ina.indexOf('=') == -1) println("no equal sign")
else ina.splitAt(ina.indexOf('='))

for(char <- ina if !char.isDigit)
  if(char == '=') println("==")
val value = 13


