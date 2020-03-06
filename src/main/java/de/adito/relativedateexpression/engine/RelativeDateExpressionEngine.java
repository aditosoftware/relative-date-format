package de.adito.relativedateexpression.engine;

import de.adito.relativedateexpression.expression.AdjustedExpression;
import de.adito.relativedateexpression.expression.FixedExpression;
import de.adito.relativedateexpression.expression.IExpression;
import de.adito.relativedateexpression.expression.MixedExpression;
import de.adito.relativedateexpression.token.ScopeToken;
import org.jetbrains.annotations.NotNull;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

public class RelativeDateExpressionEngine implements IExpressionEngine {
  @Override
  public IResult resolve(@NotNull IExpression expression, @NotNull LocalDateTime relative)
      throws ExpressionCalculateException {
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

  private IResult resolveAdjustedExpression(ScopeToken.Scope scope, LocalDateTime relative) {
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

  private IResult resolveFixedExpression(FixedExpression expression, LocalDateTime relative)
      throws ExpressionCalculateException {
    Duration startDuration = expression.getStart();
    Duration endDuration = expression.getEnd();

    LocalDateTime start = startDuration != null ? relative.plus(startDuration) : relative;
    LocalDateTime end = endDuration != null ? relative.plus(endDuration) : relative;

    if (end.isBefore(start))
      throw new ExpressionCalculateException("Start of expression is before end");

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  private IResult resolveMixedExpression(MixedExpression expression, LocalDateTime relative) {
    return resolveAdjustedExpression(
        expression.getScope(), relative.plus(expression.getDuration()));
  }
}
