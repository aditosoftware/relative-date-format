package de.adito.relativedateformat.expression;

import de.adito.relativedateformat.token.UnitToken;
import de.adito.relativedateformat.tokenizer.DefaultRelativeDateTokenizer;
import de.adito.relativedateformat.tokenizer.exception.TokenizeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractExpressionTest {
  @Test
  void shouldStringifyExpressionCorrectly() throws TokenizeException {
    DefaultRelativeDateTokenizer tokenizer = new DefaultRelativeDateTokenizer();

    String stringExpression = "REL=ADJUSTED;UNIT=DAY";

    AdjustedExpression expression = (AdjustedExpression) tokenizer.tokenize(stringExpression);

    assertNotNull(expression);
    assertEquals(UnitToken.Unit.DAY, expression.getUnit());

    assertEquals(stringExpression, expression.toString());
  }

  @Test
  void shouldPerformEqualsCheckCorrectly() throws TokenizeException {
    DefaultRelativeDateTokenizer tokenizer = new DefaultRelativeDateTokenizer();

    String stringExpression = "REL=ADJUSTED;UNIT=DAY";

    IExpression expressionOne = tokenizer.tokenize(stringExpression);
    IExpression expressionTwo = tokenizer.tokenize(stringExpression);

    assertEquals(expressionOne, expressionTwo);
  }

  @Test
  void shouldPerformEqualsCheckCorrectlyOnRandom() throws TokenizeException {
    DefaultRelativeDateTokenizer tokenizer = new DefaultRelativeDateTokenizer();

    String stringExpressionOne = "REL=FIXED;START=P1D;END=P2D";
    String stringExpressionTwo = "REL=FIXED;END=P2D;START=P1D";

    IExpression expressionOne = tokenizer.tokenize(stringExpressionOne);
    IExpression expressionTwo = tokenizer.tokenize(stringExpressionTwo);

    assertEquals(expressionOne, expressionTwo);
  }

  @Test
  void shouldFailEqualsCheckCorrectly() throws TokenizeException {
    DefaultRelativeDateTokenizer tokenizer = new DefaultRelativeDateTokenizer();

    String stringExpressionOne = "REL=MIXED;UNIT=DAY;PERIOD=P1D";
    String stringExpressionTwo = "REL=FIXED;START=P1D;END=P10D";

    IExpression expressionOne = tokenizer.tokenize(stringExpressionOne);
    IExpression expressionTwo = tokenizer.tokenize(stringExpressionTwo);

    assertNotEquals(expressionOne, expressionTwo);
  }
}
