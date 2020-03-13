package de.adito.relativedateformat.engine;

import de.adito.relativedateformat.engine.exception.EngineException;
import de.adito.relativedateformat.expression.AdjustedExpression;
import de.adito.relativedateformat.expression.FixedExpression;
import de.adito.relativedateformat.expression.IExpression;
import de.adito.relativedateformat.expression.MixedExpression;
import de.adito.relativedateformat.token.UnitToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;

public class DefaultRelativeDateEngine implements RelativeDateEngine {
  @Override
  public RelativeDateResult resolve(
      @NotNull IExpression expression, @NotNull LocalDateTime relative) throws EngineException {
    if (expression instanceof AdjustedExpression)
      return resolveAdjustedExpression(((AdjustedExpression) expression), relative);
    else if (expression instanceof FixedExpression)
      return resolveFixedExpression((FixedExpression) expression, relative);
    else if (expression instanceof MixedExpression)
      return resolveMixedExpression((MixedExpression) expression, relative);

    throw new UnsupportedOperationException(
        String.format(
            "Expression of type '%s' is not supported", expression.getClass().getSimpleName()));
  }

  private RelativeDateResult resolveAdjustedExpression(
      AdjustedExpression expression, LocalDateTime relative) {
    UnitToken.Unit unit = expression.getUnit();

    LocalDateTime start = relative;
    LocalDateTime end = relative;

    TemporalAdjuster startAdjuster = getTemporalAdjuster(true, unit);
    if (startAdjuster != null) start = start.with(startAdjuster);

    TemporalAdjuster endAdjuster = getTemporalAdjuster(false, unit);
    if (endAdjuster != null) end = end.with(endAdjuster);

    start = adjustToTime(true, start);
    end = adjustToTime(false, end);

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  private RelativeDateResult resolveFixedExpression(
      FixedExpression expression, LocalDateTime relative) {
    Period startPeriod = expression.getStart();
    Period endPeriod = expression.getEnd();
    UnitToken.Unit unit = expression.getUnit();

    LocalDateTime start = startPeriod != null ? relative.plus(startPeriod) : relative;
    LocalDateTime end = endPeriod != null ? relative.plus(endPeriod) : relative;

    if (end.isBefore(start)) throw new EngineException("Start of expression is before end");

    if (unit != null) {
      TemporalAdjuster startAdjuster = getTemporalAdjuster(true, unit);
      if (startAdjuster != null) start = start.with(startAdjuster);
      TemporalAdjuster endAdjuster = getTemporalAdjuster(false, unit);
      if (endAdjuster != null) end = end.with(endAdjuster);

      start = adjustToTime(true, start);
      end = adjustToTime(false, end);
    }

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  private RelativeDateResult resolveMixedExpression(
      MixedExpression expression, LocalDateTime relative) {
    return new DefaultResult(null, null, null);
  }

  @Nullable
  private TemporalAdjuster getTemporalAdjuster(boolean start, UnitToken.Unit unit) {
    switch (unit) {
      case YEAR:
        if (start) return TemporalAdjusters.firstDayOfYear();
        else return TemporalAdjusters.lastDayOfYear();
      case MONTH:
        if (start) return TemporalAdjusters.firstDayOfMonth();
        else return TemporalAdjusters.lastDayOfMonth();
      case WEEK:
        if (start) return DayOfWeek.MONDAY;
        else return DayOfWeek.SUNDAY;
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

  private TemporalUnit getTemporalUnit(UnitToken.Unit unit) {
    switch (unit) {
      case YEAR:
        return ChronoUnit.YEARS;
      case MONTH:
        return ChronoUnit.MONTHS;
      case WEEK:
        return ChronoUnit.WEEKS;
      case DAY:
      default:
        return ChronoUnit.DAYS;
    }
  }
}
