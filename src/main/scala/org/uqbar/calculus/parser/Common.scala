package org.uqbar.thin.parsers

case class ParseException(message: String) extends RuntimeException(message)
case class DefinitionsException(message: String) extends RuntimeException(message)