package org.uqbar.thin.calculus
import org.scalatest.FreeSpec
import org.scalatest.Matchers

class CalculusDefinitions extends FreeSpec with Matchers with CalculusParser {

  "Calculus Definitions" - {

    "should process" - {

      "simple expression" in {
          val definition = "true  = λt. λf. t;"
          val parsed = new LambdaCalculusParser().definitions(definition)
          
      }
      
      "expressions with triple quote" in {
          val definition = """true  = λt. λf. t;
          false = λt. λf. f;"""
          val parsed = new LambdaCalculusParser().definitions(definition)
          println(parsed)
      }
      
      "empty expression" in {
        val definitions = new Definitions().load()
        print(definitions)
        definitions should be (1)
      }
      
    }
  }
}