package de.adito.relativedateexpression.tokenizer;

import de.adito.relativedateexpression.expression.AdjustedExpression;
import de.adito.relativedateexpression.expression.FixedExpression;
import de.adito.relativedateexpression.expression.IExpression;
import de.adito.relativedateexpression.expression.MixedExpression;
import de.adito.relativedateexpression.token.ScopeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultExpressionTokenizerTest {
  private DefaultExpressionTokenizer tokenizer;

  @BeforeEach
  void init() {
    tokenizer = new DefaultExpressionTokenizer();
  }

  @Test
  void shouldTokenizeAdjustedExpression() throws ExpressionTokenizeException {
    IExpression expression = tokenizer.tokenize("REL=ADJUSTED;SCOPE=DAY");

    assertTrue(expression instanceof AdjustedExpression);

    AdjustedExpression exp = (AdjustedExpression) expression;

    assertEquals(ScopeToken.Scope.DAY, exp.getScope());
  }

  @Test
  void shouldTokenizeFixedExpression() throws ExpressionTokenizeException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P1D;END=null");

    assertTrue(expression instanceof FixedExpression);

    FixedExpression exp = (FixedExpression) expression;

    assertEquals(Duration.ofDays(1).negated(), exp.getStart());
    assertEquals(Duration.ZERO, exp.getEnd());
  }

  @Test
  void shouldTokenizeMixedExpression() throws ExpressionTokenizeException {
    IExpression expression = tokenizer.tokenize("REL=MIXED;DURATION=P30D;SCOPE=MONTH");

    assertTrue(expression instanceof MixedExpression);

    MixedExpression exp = (MixedExpression) expression;

    assertEquals(Duration.of(30, ChronoUnit.DAYS), exp.getDuration());
    assertEquals(ScopeToken.Scope.MONTH, exp.getScope());
  }
}
