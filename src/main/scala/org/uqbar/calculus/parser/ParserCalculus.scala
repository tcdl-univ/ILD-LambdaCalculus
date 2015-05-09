package org.uqbar.thin.calculus

import scala.util.parsing.combinator.syntactical.StdTokenParsers
import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.combinator._

class LambdaCalculusParser extends CalculusParser;

object CalculusParser extends CalculusParser
trait CalculusParser extends StdTokenParsers with PackratParsers {
  type Tokens = StdLexical
  val lexical = new LambdaLexer
  lexical.delimiters ++= Seq("lambda", "\\", "λ", ".", "(", ")", "=", ";")

  type Parser[+T] = PackratParser[T]
  lazy val expr: Parser[Expression] = application | statement
  lazy val statement: Parser[Expression] = variable | number | parens | lambda
  lazy val lambda = positioned(("λ" | "\\" | "lambda") ~> variable ~ "." ~ expr ^^ { case v ~ "." ~ e => Lambda(v, e) })
  lazy val application = positioned(expr ~ statement ^^ { case left ~ right => Apply(left, right) })
  lazy val variable = positioned(ident ^^ Variable.apply)
  lazy val parens: Parser[Expression] = "(" ~> expr <~ ")"
  lazy val number = numericLit ^^ { case n => Integer(n.toInt) }

  lazy val defs = repsep(defn, ";") <~ opt(";") ^^ Map().++
  lazy val defn = ident ~ "=" ~ expr ^^ { case id ~ "=" ~ t => id -> t }

  def apply(input: String) = parse(input) match {
    case Success(result, _) => result
    case NoSuccess(msg, _)  => throw ParseException(msg)
  }

  def applyDefinitions(input: String) = definitions(input) match {
    case Success(result, _) => result
    case NoSuccess(msg, _)  => throw DefinitionsException(msg)
  }

  def definitions(str: String): ParseResult[Map[String, Expression]] = {
    val tokens = new lexical.Scanner(str)
    phrase(defs)(tokens)
  }

  def parse(str: String): ParseResult[Expression] = {
    val tokens = new lexical.Scanner(str)
    phrase(expr)(tokens)
  }
}

class LambdaLexer extends StdLexical {
  override def letter = elem("letter", c => c.isLetter && c != 'λ')
}