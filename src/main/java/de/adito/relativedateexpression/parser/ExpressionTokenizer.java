package de.adito.relativedateexpression.parser;

import de.adito.relativedateexpression.expression.IExpression;

public interface ExpressionTokenizer {
  IExpression tokenize (String expression) throws ExpressionParseException;
}
