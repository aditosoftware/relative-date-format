package de.adito.relativedateformat.tokenizer;

import de.adito.relativedateformat.expression.AdjustedExpression;
import de.adito.relativedateformat.expression.FixedExpression;
import de.adito.relativedateformat.expression.IExpression;
import de.adito.relativedateformat.expression.MixedExpression;
import de.adito.relativedateformat.token.UnitToken;
import de.adito.relativedateformat.tokenizer.exception.TokenizeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultRelativeDateTokenizerTest {
  private DefaultRelativeDateTokenizer tokenizer;

  @BeforeEach
  void init() {
    tokenizer = new DefaultRelativeDateTokenizer();
  }

  @Test
  void shouldTokenizeAdjustedExpression() throws TokenizeException {
    IExpression expression = tokenizer.tokenize("REL=ADJUSTED;UNIT=DAY");

    assertTrue(expression instanceof AdjustedExpression);

    AdjustedExpression exp = (AdjustedExpression) expression;

    assertEquals(UnitToken.Unit.DAY, exp.getUnit());
  }

  @Test
  void shouldTokenizeFixedExpression() throws TokenizeException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P1D;END=null");

    assertTrue(expression instanceof FixedExpression);

    FixedExpression exp = (FixedExpression) expression;

    assertEquals(Period.ofDays(1).negated(), exp.getStart());
    assertEquals(Period.ZERO, exp.getEnd());
  }

  @Test
  void shouldTokenizeMixedExpression() throws TokenizeException {
    IExpression expression = tokenizer.tokenize("REL=MIXED;PERIOD=P30D;UNIT=MONTH");

    assertTrue(expression instanceof MixedExpression);

    MixedExpression exp = (MixedExpression) expression;

    assertEquals(Period.ofDays(30), exp.getPeriod());
    assertEquals(UnitToken.Unit.MONTH, exp.getScope());
  }
}
