package org.uqbar.thin.calculus


case class ParseException(message: String) extends RuntimeException(message)
case class DefinitionsException(e: Exception) extends RuntimeException(e)