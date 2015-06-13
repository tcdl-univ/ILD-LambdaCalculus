package org.uqbar.thin.calculus

object CNumber {
  def apply(x: Int) = {
    var cn: Expression = Variable("z")
    for (i <- 1 to x)
      cn = Apply(Variable("s"), cn)
    Lambda(Variable("s"), Lambda(Variable("z"), cn))
  }

  def unapply(expr: Expression): Option[Int] = expr match {
    case Variable("Z", Scope.TOP)             => Some(0)
    case Apply(Variable("S", Scope.TOP), arg) => unapply(arg) map (_ + 1)
    case _                                    => None
  }
}

object CBoolean {
  def unapply(expr: Expression): Option[Boolean] = expr match {
    case Variable("T", Scope.TOP) => Some(true)
    case Variable("F", Scope.TOP) => Some(false)
    case _                        => None
  }
}