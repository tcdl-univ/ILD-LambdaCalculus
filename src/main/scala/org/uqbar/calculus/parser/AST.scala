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

case class Message(pos: Position, msg: String)
