

/*val test = List("2","=","2")

val head: String => (String, Char) = s => (s.tail, s.head)
val toInt: String => (String, Int) = s => ("", s.toInt)
val product: String => (String, Int) = s => {
  val (state, ch) = head(s)
  val (state2, i) = toInt(state)
  (state2, ch * i)
}

val initialState = "23"
product(initialState)*/