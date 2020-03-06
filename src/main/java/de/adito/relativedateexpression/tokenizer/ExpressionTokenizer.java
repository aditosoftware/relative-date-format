package de.adito.relativedateexpression.tokenizer;

import de.adito.relativedateexpression.expression.IExpression;

public interface ExpressionTokenizer {
  IExpression tokenize (String expression) throws ExpressionTokenizeException;
}
