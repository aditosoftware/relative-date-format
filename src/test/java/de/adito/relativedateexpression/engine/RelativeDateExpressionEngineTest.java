package de.adito.relativedateexpression.engine;

import de.adito.relativedateexpression.expression.IExpression;
import de.adito.relativedateexpression.tokenizer.DefaultExpressionTokenizer;
import de.adito.relativedateexpression.tokenizer.ExpressionTokenizeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

class RelativeDateExpressionEngineTest {
  private DefaultExpressionTokenizer tokenizer;
  private RelativeDateExpressionEngine engine;
  private LocalDateTime now;

  @BeforeEach
  void init() {
    tokenizer = new DefaultExpressionTokenizer();
    engine = new RelativeDateExpressionEngine();
    now = LocalDateTime.now();
  }

  @Test
  void shouldResolveAdjustedExpressionCorrectly()
      throws ExpressionTokenizeException, ExpressionCalculateException {
    IExpression expression = tokenizer.tokenize("REL=ADJUSTED;SCOPE=YEAR");
    IResult result = engine.resolve(expression, now);

    assertNotNull(result);

    Duration expected =
        Duration.between(
            now.with(TemporalAdjusters.firstDayOfYear()),
            now.with(TemporalAdjusters.lastDayOfYear()));

    assertEquals(now.with(TemporalAdjusters.firstDayOfYear()), result.getStart());
    assertEquals(expected, result.getDuration());
  }

  @Test
  void shouldResolveFixedExpressionCorrectly()
      throws ExpressionTokenizeException, ExpressionCalculateException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P10D");
    IResult result = engine.resolve(expression, now);

    assertNotNull(result);

    LocalDateTime start = now.minus(Duration.ofDays(10));
    Duration expected = Duration.between(start, now);

    assertEquals(start, result.getStart());
    assertEquals(expected, result.getDuration());
  }

  @Test
  void shouldThrowOnInvalidFixedExpression() throws ExpressionTokenizeException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P1D;END=-P2D");

    assertThrows(ExpressionCalculateException.class, () -> engine.resolve(expression, now));
  }

  @Test
  void shouldResolveMixedExpression()
      throws ExpressionTokenizeException, ExpressionCalculateException {
    IExpression expression = tokenizer.tokenize("REL=MIXED;DURATION=P300D;SCOPE=MONTH");
    IResult result = engine.resolve(expression, now);

    assertNotNull(result);

    Duration _300Duration = Duration.ofDays(300);
    LocalDateTime start = now.plus(_300Duration).with(TemporalAdjusters.firstDayOfMonth());
    LocalDateTime end = now.plus(_300Duration).with(TemporalAdjusters.lastDayOfMonth());
    Duration expected = Duration.between(start, end);

    assertEquals(start, result.getStart());
    assertEquals(expected, result.getDuration());
  }
}
