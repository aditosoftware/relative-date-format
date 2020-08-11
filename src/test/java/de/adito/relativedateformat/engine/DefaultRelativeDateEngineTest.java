package de.adito.relativedateformat.engine;

import de.adito.relativedateformat.engine.exception.EngineException;
import de.adito.relativedateformat.expression.IExpression;
import de.adito.relativedateformat.tokenizer.DefaultRelativeDateTokenizer;
import de.adito.relativedateformat.tokenizer.exception.TokenizeException;
import org.junit.jupiter.api.*;

import java.time.*;
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
  void shouldResolveAdjustedExpressionCorrectly() {
    IExpression expression = tokenizer.tokenize("REL=ADJUSTED;UNIT=YEAR");
    RelativeDateResult result = engine.resolve(expression, now);

    assertNotNull(result);

    assertEquals(
        now.with(TemporalAdjusters.firstDayOfYear()).toLocalDate().atTime(LocalTime.MIN),
        result.getStart());

    assertEquals(
        now.with(TemporalAdjusters.lastDayOfYear()).toLocalDate().atTime(LocalTime.MAX),
        result.getEnd());
  }

  @Test
  void shouldResolveFixedExpressionCorrectly() throws TokenizeException, EngineException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P10D");
    RelativeDateResult result = engine.resolve(expression, now);

    assertNotNull(result);

    LocalDateTime start = now.minus(Duration.ofDays(10));
    Duration expected = Duration.between(start, now);

    assertEquals(start, result.getStart());
    assertEquals(expected, result.getDuration());
  }

  @Test
  void shouldResolveFixedExpressionWithUnitCorrectly() {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P2W;UNIT=WEEK");
    RelativeDateResult result = engine.resolve(expression, now);

    assertNotNull(result);

    LocalDateTime start =
        now.minus(Period.ofWeeks(2)).with(DayOfWeek.MONDAY).toLocalDate().atTime(LocalTime.MIN);

    LocalDateTime end = now.with(DayOfWeek.SUNDAY).toLocalDate().atTime(LocalTime.MAX);

    assertEquals(start, result.getStart());
    assertEquals(end, result.getEnd());
  }

  @Test
  void shouldThrowOnInvalidFixedExpression() throws TokenizeException {
    IExpression expression = tokenizer.tokenize("REL=FIXED;START=-P1D;END=-P2D");

    assertThrows(EngineException.class, () -> engine.resolve(expression, now));
  }

  @Test
  void shouldResolveAdjustedExpressionWithCustomFirstDayOfWeekCorrectly() {
    IExpression expression = tokenizer.tokenize("REL=ADJUSTED;UNIT=WEEK");
    RelativeDateResult result =
        engine.resolve(
            expression,
            LocalDateTime.parse("2020-08-10T09:47:26"),
            new RelativeDateEngineProperties(DayOfWeek.SUNDAY));

    assertNotNull(result);

    assertEquals(
        LocalDateTime.parse("2020-08-09T00:00:00").toLocalDate().atTime(LocalTime.MIN),
        result.getStart());

    assertEquals(
        LocalDateTime.parse("2020-08-15T00:00:00").toLocalDate().atTime(LocalTime.MAX),
        result.getEnd());
  }

  /*  @Test
  void shouldResolveMixedExpression()
      throws TokenizeException, EngineException {
    IExpression expression = tokenizer.tokenize("REL=MIXED;DURATION=P300D;UNIT=MONTH");
    RelativeDateResult result = engine.resolve(expression, now);

    assertNotNull(result);

    Duration _300Duration = Duration.ofDays(300);
    LocalDateTime start = now.plus(_300Duration).with(TemporalAdjusters.firstDayOfMonth());
    LocalDateTime end = now.plus(_300Duration).with(TemporalAdjusters.lastDayOfMonth());
    Duration expected = Duration.between(start, end);

    assertEquals(start, result.getStart());
    assertEquals(expected, result.getDuration());
  }*/
}
