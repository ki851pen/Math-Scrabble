val a = List(List("2","=","2","","3","=","3","","2","=","1","0"))

def findEquation(lol: List[List[String]]): List[List[String]] = {
  val r  = lol.reverse
  val l  = r.head
  val wolast = r.tail.reverse
  val x = l.indexOf("")

  val c = if (x==0) l.splitAt(1) else l.splitAt(x)
  val list: List[List[String]] = c._1::c._2::Nil
  val a = wolast ::: list.filter(_.length >= 3)
  if (a.forall(!_.contains(""))) {
    a
  } else findEquation(a)
}

val b = findEquation(a)

val test = List("2","=","2")

val head: String => (String, Char) = s => (s.tail, s.head)
val toInt: String => (String, Int) = s => ("", s.toInt)
val product: String => (String, Int) = s => {
  val (state, ch) = head(s)
  val (state2, i) = toInt(state)
  (state2, ch * i)
}

val initialState = "23"
product(initialState)

/*def eval(ex: List[String]): Boolean = {
  ex.head.toIntOption.getOrElse(return false)
  ex
}*/