package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.tokenizer.DefaultExpressionTokenizer;
import de.adito.relativedateexpression.tokenizer.ExpressionTokenizeException;
import de.adito.relativedateexpression.token.ScopeToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
