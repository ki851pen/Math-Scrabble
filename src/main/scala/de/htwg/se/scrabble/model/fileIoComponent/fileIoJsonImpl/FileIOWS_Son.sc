//object HelloWorld {
//  def apply(string: String)
//}
import play.api.libs.json._

case class HelloWorld(json: String, intVal: Int)

object HelloWorld {
  def apply(module: String, intModule: Int) = new HelloWorld(module, intModule)
  //implicit val hwWrites = Json.writes[HelloWorld]
  //implicit val hwReads = Json.reads[HelloWorld]
  implicit val hwFormat = Json.format[HelloWorld]
}
var jsonStr = Json.stringify(Json.toJson(HelloWorld("Json", 0)))

var jsValue = Json.parse(jsonStr)
var jsPath = JsPath \ "module"
jsValue.validate[HelloWorld]
