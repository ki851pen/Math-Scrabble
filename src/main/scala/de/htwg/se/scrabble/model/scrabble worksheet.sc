val a = List(List("","","","3","=","3","","2"))

def doit(lol: List[List[String]]): List[List[String]] = {
  val r  = lol.reverse
  val l  = r.head
  val wolast = r.tail.reverse
  val x = l.indexOf("")

  val c = if (x==0) l.splitAt(1) else l.splitAt(x)
  val list: List[List[String]] = c._1::c._2::Nil
  val a = wolast ::: list.filter(_.length >= 3)
  if (a.forall(!_.contains(""))) {
    a
  } else doit(a)
}

val b = doit(a)