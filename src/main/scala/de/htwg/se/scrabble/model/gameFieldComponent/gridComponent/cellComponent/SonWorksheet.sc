class TestClass(val intVal: Int, stringVal: String){}
case class TestCaseClass(intVal: Int, stringVal: String){}

//var classInstance = TestClass  not valid
var classInstance = new TestClass(1, "one");
var testCaseClass = new TestCaseClass(1, "one") // no need new
classInstance.intVal
//classInstance.stringVal   stringVal is not val
testCaseClass.intVal  //intVal is auto val
testCaseClass.stringVal
classInstance.toString
testCaseClass.toString