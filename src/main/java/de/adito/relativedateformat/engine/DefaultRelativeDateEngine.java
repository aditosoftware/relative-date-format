package de.adito.relativedateformat.engine;

import de.adito.relativedateformat.engine.exception.EngineException;
import de.adito.relativedateformat.expression.AdjustedExpression;
import de.adito.relativedateformat.expression.FixedExpression;
import de.adito.relativedateformat.expression.IExpression;
import de.adito.relativedateformat.expression.MixedExpression;
import de.adito.relativedateformat.token.ScopeToken;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class DefaultRelativeDateEngine implements RelativeDateEngine {
  @Override
  public RelativeDateResult resolve(@NotNull IExpression expression, @NotNull LocalDateTime relative)
      throws EngineException {
    if (expression instanceof AdjustedExpression)
      return resolveAdjustedExpression(((AdjustedExpression) expression).getScope(), relative);
    else if (expression instanceof FixedExpression)
      return resolveFixedExpression((FixedExpression) expression, relative);
    else if (expression instanceof MixedExpression)
      return resolveMixedExpression((MixedExpression) expression, relative);

    throw new UnsupportedOperationException(
        String.format(
            "Expression of type '%s' is not supported", expression.getClass().getSimpleName()));
  }

  private RelativeDateResult resolveAdjustedExpression(ScopeToken.Scope scope, LocalDateTime relative) {
    LocalDateTime start;
    LocalDateTime end;

    if (scope == ScopeToken.Scope.YEAR) {
      start = relative.with(TemporalAdjusters.firstDayOfYear());
      end = relative.with(TemporalAdjusters.lastDayOfYear());
    } else if (scope == ScopeToken.Scope.MONTH) {
      start = relative.with(TemporalAdjusters.firstDayOfMonth());
      end = relative.with(TemporalAdjusters.lastDayOfMonth());
    } else if (scope == ScopeToken.Scope.WEEK) {
      start = relative.with(DayOfWeek.MONDAY);
      end = relative.with(DayOfWeek.SUNDAY);
    } else {
      LocalDate date = relative.toLocalDate();

      start = date.atTime(LocalTime.MIN);
      end = date.atTime(LocalTime.MAX);
    }

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  private RelativeDateResult resolveFixedExpression(FixedExpression expression, LocalDateTime relative)
      throws EngineException {
    Duration startDuration = expression.getStart();
    Duration endDuration = expression.getEnd();

    LocalDateTime start = startDuration != null ? relative.plus(startDuration) : relative;
    LocalDateTime end = endDuration != null ? relative.plus(endDuration) : relative;

    if (end.isBefore(start))
      throw new EngineException("Start of expression is before end");

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  private RelativeDateResult resolveMixedExpression(MixedExpression expression, LocalDateTime relative) {
    return resolveAdjustedExpression(
        expression.getScope(), relative.plus(expression.getDuration()));
  }
}
