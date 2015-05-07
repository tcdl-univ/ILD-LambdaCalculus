package org.uqbar.thin.calculus
import org.scalatest.FreeSpec
import org.scalatest.Matchers

class CalculusTest extends FreeSpec with Matchers with CalculusParser {

  "Calculus Parser" - {

    "should parse" - {

      "empty expression" in {
        parse("λx.x") should be(Lambda(Variable("x", new Scope(None, Set())), Variable("x", new Scope(None, Set()))))
      }

      "simple one lambda expression" in {
       parse("λs.λz. s z") should be(Lambda(Variable("s", new Scope(None, Set())), Lambda(Variable("z", new Scope(None, Set())), Apply(Variable("s", new Scope(None, Set())), Variable("z", new Scope(None, Set()))))))
      }
      
      "simple recursive expression" in {  
        parse("4") should be(Integer(4))
      }
    }
  }
}