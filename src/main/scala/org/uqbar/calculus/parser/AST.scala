package org.uqbar.thin.calculus

import scala.util.parsing.input.Positional
import scala.collection.mutable.ListBuffer
import scala.util.parsing.input.Position

sealed trait Expression extends Positional

case class Integer(i: Int) extends Expression
case class Identifier(s: String) extends Expression

object Variable {
  def apply(name: String): Variable = Variable(name, Scope.TOP)
}
case class Variable(name: String, scope: Scope) extends Expression

case class Lambda(variable: Variable, expression: Expression) extends Expression
case class Apply(fun: Expression, arg: Expression) extends Expression

object Scope {
  var id = 0
  def nextId = { val i = id; id += 1; i }
  val TOP = new Scope(None, Set())
}

class Scope(val parent: Option[Scope], val boundNames: Set[String]) {

  val id = Scope.nextId

  def closestBinding(name: String): Option[Scope] =
    if (boundNames contains name)
      Some(this)
    else
      parent flatMap (_ closestBinding name)

}

case class Message(pos: Position, msg: String)

object CNumber {
  def apply(n: Int) = {
    var cn: Expression = Variable("z")
    for (i <- 1 to n)
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