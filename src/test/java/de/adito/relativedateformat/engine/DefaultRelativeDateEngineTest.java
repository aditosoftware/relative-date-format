package de.adito.relativedateformat.engine;

import de.adito.relativedateformat.engine.exception.EngineException;
import de.adito.relativedateformat.expression.IExpression;
import de.adito.relativedateformat.tokenizer.DefaultRelativeDateTokenizer;
import de.adito.relativedateformat.tokenizer.exception.TokenizeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRelativeDateEngineTest {
  private DefaultRelativeDateTokenizer tokenizer;
  private DefaultRelativeDateEngine engine;
  private LocalDateTime now;

  @BeforeEach
  void init() {
    tokenizer = new DefaultRelativeDateTokenizer();
    engine = new DefaultRelativeDateEngine();
    now = LocalDateTime.now();
  }

  @Test
  void shouldResolveAdjustedExpressionCorrectly()
      throws TokenizeException, EngineException {
    IExpression expression = tokenizer.tokenize("REL=ADJUSTED;SCOPE=YEAR");
    RelativeDateResult result = engine.resolve(expression, now);

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
      throws TokenizeException, EngineException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P10D");
    RelativeDateResult result = engine.resolve(expression, now);

    assertNotNull(result);

    LocalDateTime start = now.minus(Duration.ofDays(10));
    Duration expected = Duration.between(start, now);

    assertEquals(start, result.getStart());
    assertEquals(expected, result.getDuration());
  }

  @Test
  void shouldThrowOnInvalidFixedExpression() throws TokenizeException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P1D;END=-P2D");

    assertThrows(EngineException.class, () -> engine.resolve(expression, now));
  }

  @Test
  void shouldResolveMixedExpression()
      throws TokenizeException, EngineException {
    IExpression expression = tokenizer.tokenize("REL=MIXED;DURATION=P300D;SCOPE=MONTH");
    RelativeDateResult result = engine.resolve(expression, now);

    assertNotNull(result);

    Duration _300Duration = Duration.ofDays(300);
    LocalDateTime start = now.plus(_300Duration).with(TemporalAdjusters.firstDayOfMonth());
    LocalDateTime end = now.plus(_300Duration).with(TemporalAdjusters.lastDayOfMonth());
    Duration expected = Duration.between(start, end);

    assertEquals(start, result.getStart());
    assertEquals(expected, result.getDuration());
  }
}
