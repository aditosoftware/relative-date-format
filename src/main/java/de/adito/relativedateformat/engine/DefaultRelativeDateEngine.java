package de.adito.relativedateformat.engine;

import de.adito.relativedateformat.engine.day.WeekBoundsCalculator;
import de.adito.relativedateformat.engine.exception.EngineException;
import de.adito.relativedateformat.expression.*;
import de.adito.relativedateformat.token.UnitToken;
import org.jetbrains.annotations.*;

import java.time.*;
import java.time.temporal.*;

public class DefaultRelativeDateEngine implements RelativeDateEngine {
  @Override
  public RelativeDateResult resolve(
      @NotNull IExpression expression,
      @NotNull LocalDateTime relative,
      @Nullable RelativeDateEngineProperties properties) {
    if (expression instanceof AdjustedExpression)
      return resolveAdjustedExpression(((AdjustedExpression) expression), relative, properties);
    else if (expression instanceof FixedExpression)
      return resolveFixedExpression((FixedExpression) expression, relative, properties);
    else if (expression instanceof MixedExpression)
      throw new UnsupportedOperationException("MixedExpression not yet supported");

    throw new UnsupportedOperationException(
        String.format(
            "Expression of type '%s' is not supported", expression.getClass().getSimpleName()));
  }

  private RelativeDateResult resolveAdjustedExpression(
      AdjustedExpression expression,
      LocalDateTime relative,
      @Nullable RelativeDateEngineProperties properties) {
    UnitToken.Unit unit = expression.getUnit();

    LocalDateTime start = relative;
    LocalDateTime end = relative;

    TemporalAdjuster startAdjuster = getTemporalAdjuster(true, unit, properties);
    if (startAdjuster != null) start = start.with(startAdjuster);

    TemporalAdjuster endAdjuster = getTemporalAdjuster(false, unit, properties);
    if (endAdjuster != null) end = end.with(endAdjuster);

    start = adjustToTime(true, start);
    end = adjustToTime(false, end);

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  private RelativeDateResult resolveFixedExpression(
      FixedExpression expression, LocalDateTime relative, RelativeDateEngineProperties properties) {
    Period startPeriod = expression.getStart();
    Period endPeriod = expression.getEnd();
    UnitToken.Unit unit = expression.getUnit();

    LocalDateTime start = startPeriod != null ? relative.plus(startPeriod) : relative;
    LocalDateTime end = endPeriod != null ? relative.plus(endPeriod) : relative;

    if (end.isBefore(start)) throw new EngineException("Start of expression is before end");

    if (unit != null) {
      TemporalAdjuster startAdjuster = getTemporalAdjuster(true, unit, properties);
      if (startAdjuster != null) start = start.with(startAdjuster);
      TemporalAdjuster endAdjuster = getTemporalAdjuster(false, unit, properties);
      if (endAdjuster != null) end = end.with(endAdjuster);

      start = adjustToTime(true, start);
      end = adjustToTime(false, end);
    }

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  @Nullable
  private TemporalAdjuster getTemporalAdjuster(
      boolean start, UnitToken.Unit unit, RelativeDateEngineProperties properties) {

    switch (unit) {
      case YEAR:
        if (start) return TemporalAdjusters.firstDayOfYear();
        else return TemporalAdjusters.lastDayOfYear();
      case MONTH:
        if (start) return TemporalAdjusters.firstDayOfMonth();
        else return TemporalAdjusters.lastDayOfMonth();
      case WEEK:
        WeekBoundsCalculator.Bounds bounds = getWeekBounds(properties);

        if (start) return bounds.start;
        else return bounds.end;
      case DAY:
      default:
        return null;
    }
  }

  @NotNull
  private LocalDateTime adjustToTime(boolean start, LocalDateTime dateTime) {
    if (start) return dateTime.toLocalDate().atTime(LocalTime.MIN);
    else return dateTime.toLocalDate().atTime(LocalTime.MAX);
  }

  /**
   * Will calculate the bounds for the week based on the given properties. The properties may hold
   * the first day of the week. If no explicit first day of week is defined, we use {@link
   * DayOfWeek#MONDAY}.
   *
   * @param properties The properties which hold the first day of week.
   * @return The bounds for the week.
   */
  private WeekBoundsCalculator.Bounds getWeekBounds(RelativeDateEngineProperties properties) {
    DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;

    // Load the first day of week from the properties if available.
    if (properties != null && properties.getFirstDayOfWeek() != null)
      firstDayOfWeek = properties.getFirstDayOfWeek();

    // Calculate the bounds.
    return WeekBoundsCalculator.getBounds(firstDayOfWeek);
  }
}
