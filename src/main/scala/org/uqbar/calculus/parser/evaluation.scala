package org.uqbar.thin.calculus

class Evaluation(debug: Boolean = false) {

  def apply(term: Expression): Expression =
    try {
      val term2 = evalStep(term)
      if (debug)
        println(s"step: ${println(term)} → ${println(term2)}")
      apply(term2)
    } catch {
      case _: MatchError => term
    }

  def evalStep(term: Expression): Expression = term match {
    case Apply(Lambda(argDef, body), arg) if isValue(arg) =>
      new Substitution(argDef, arg)(body)
    case Apply(fun, arg) if isValue(fun) =>
      Apply(fun, evalStep(arg))
    case Apply(fun, arg) =>
      Apply(evalStep(fun), arg)
  }

  def isValue(term: Expression): Boolean = term match {
    case _: Lambda  => true
    case _: Variable     => true
    case CNumber(_) => true
    case _          => false
  }

}

class Substitution(argV: Variable, replacement: Expression) {

  val binder = new Binder(Map())

  def apply(term: Expression): Expression = term match {
    case Variable(argV.name, argV.scope) => binder.bind(replacement, argV.scope.parent.get)
    case Variable(_, _)                  => term
    case Apply(fun, arg)            => Apply(apply(fun), apply(arg))
    case Lambda(arg, body)          => Lambda(arg, apply(body))
  }

}