package org.uqbar.thin.calculus

object Definitions {
 val definitions = """succ = λn.λs.λz. s (n s z);
    true  = λt. λf. t;
    false = λt. λf. f;
    
    first  = λp. p true;
    second = λp. p false;"""
 
  def apply() {
   val parser = new LambdaCalculusParser()
   parser.applyDefinitions(definitions) 
 } 
}
