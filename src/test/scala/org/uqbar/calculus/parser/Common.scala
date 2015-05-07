package org.uqbar.thin.parsers

import org.scalatest.FreeSpec
import org.scalatest.Matchers
import scala.util.parsing.combinator._
import org.scalatest.matchers.Matcher
import scala.util.parsing.input.Reader
import org.scalatest.matchers.MatchResult

abstract class ParserTest extends FreeSpec with Matchers with Parsers {

	def parseAll[T](p: Parser[T], in: CharSequence): ParseResult[T]

	case class beParsedTo[T](expected: T)(implicit parser: Parser[T]) extends Matcher[String] {
		def apply(target: String) = {
			val result = parseAll(parser, target)
			MatchResult(
				result.successful && result.get == expected,
				if (result.successful) s"Parsed ${result.get} did not equal $expected" else s"Parse failed! $result",
				if (result.successful) s"Parsed ${result.get} was equal to $expected" else s"Parse didn't fail! $result"
			)
		}
	}

	case class beParsed[T](implicit parser: Parser[T]) extends Matcher[String] {
		def apply(target: String) = {
			val result = parseAll(parser, target)
			MatchResult(
				result.successful,
				s"Parse failed: $result",
				s"Parse didn't fail! $result"
			)
		}
	}

	def notBeParsed[T](implicit parser: Parser[T]) = not(beParsed()(parser))

}