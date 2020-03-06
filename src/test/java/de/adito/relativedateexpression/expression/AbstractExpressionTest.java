package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.token.ScopeToken;
import de.adito.relativedateexpression.tokenizer.DefaultExpressionTokenizer;
import de.adito.relativedateexpression.tokenizer.ExpressionTokenizeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractExpressionTest {
  @Test
  void shouldStringifyExpressionCorrectly() throws ExpressionTokenizeException {
    DefaultExpressionTokenizer tokenizer = new DefaultExpressionTokenizer();

    String stringExpression = "REL=ADJUSTED;SCOPE=DAY";

    AdjustedExpression expression = (AdjustedExpression) tokenizer.tokenize(stringExpression);

    assertNotNull(expression);
    assertEquals(ScopeToken.Scope.DAY, expression.getScope());

    assertEquals(stringExpression, expression.toString());
  }

  @Test
  void shouldPerformEqualsCheckCorrectly() throws ExpressionTokenizeException {
    DefaultExpressionTokenizer tokenizer = new DefaultExpressionTokenizer();

    String stringExpression = "REL=ADJUSTED;SCOPE=DAY";

    IExpression expressionOne = tokenizer.tokenize(stringExpression);
    IExpression expressionTwo = tokenizer.tokenize(stringExpression);

    assertEquals(expressionOne, expressionTwo);
  }

  @Test
  void shouldPerformEqualsCheckCorrectlyOnRandom() throws ExpressionTokenizeException {
    DefaultExpressionTokenizer tokenizer = new DefaultExpressionTokenizer();

    String stringExpressionOne = "REL=FIXED;START=P1D;END=P2D";
    String stringExpressionTwo = "REL=FIXED;END=P2D;START=P1D";

    IExpression expressionOne = tokenizer.tokenize(stringExpressionOne);
    IExpression expressionTwo = tokenizer.tokenize(stringExpressionTwo);

    assertEquals(expressionOne, expressionTwo);
  }

  @Test
  void shouldFailEqualsCheckCorrectly() throws ExpressionTokenizeException {
    DefaultExpressionTokenizer tokenizer = new DefaultExpressionTokenizer();

    String stringExpressionOne = "REL=MIXED;SCOPE=DAY;DURATION=P1D";
    String stringExpressionTwo = "REL=FIXED;START=P1D;END=P10D";

    IExpression expressionOne = tokenizer.tokenize(stringExpressionOne);
    IExpression expressionTwo = tokenizer.tokenize(stringExpressionTwo);

    assertNotEquals(expressionOne, expressionTwo);
  }
}
