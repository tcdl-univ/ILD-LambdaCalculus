package org.uqbar.thin.calculus

import scala.util.parsing.combinator._

class Definitions {
  val definitions = """succ = λn.λs.λz. s (n s z);
    true  = λt. λf. t;
    false = λt. λf. f;
    
    first  = λp. p true;
    second = λp. p false;"""

  def load(): Map[String, Expression] = {
    import parser.{ Success, NoSuccess }
    val parser = new LambdaCalculusParser()
    parser.definitions(definitions) match {
      case Success(result, _) => result
      case NoSuccess(msg, _)  => throw ParseException(msg)
    }
  }

}
