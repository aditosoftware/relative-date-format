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
      return resolveAdjustedExpression((AdjustedExpression) expression, relative);
    else if (expression instanceof FixedExpression)
      return resolveFixedExpression((FixedExpression) expression, relative);
    else if (expression instanceof MixedExpression)
      return resolveMixedExpression((MixedExpression) expression, relative);

    throw new UnsupportedOperationException(
        String.format(
            "Expression of type '%s' is not supported", expression.getClass().getSimpleName()));
  }

  private IResult resolveAdjustedExpression(AdjustedExpression expression, LocalDateTime relative) {
    LocalDateTime start;
    LocalDateTime end;

    if (expression.getScope() == ScopeToken.Scope.YEAR) {
      start = relative.with(TemporalAdjusters.firstDayOfYear());
      end = relative.with(TemporalAdjusters.lastDayOfYear());
    } else if (expression.getScope() == ScopeToken.Scope.MONTH) {
      start = relative.with(TemporalAdjusters.firstDayOfMonth());
      end = relative.with(TemporalAdjusters.lastDayOfMonth());
    } else if (expression.getScope() == ScopeToken.Scope.WEEK) {
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
    LocalDateTime start = relative.plus(expression.getStart());
    LocalDateTime end = relative.plus(expression.getEnd());

    if (end.isBefore(start))
      throw new ExpressionCalculateException("Start of expression is before end");

    return new DefaultResult(start, end, Duration.between(start, end));
  }

  private IResult resolveMixedExpression(MixedExpression expression, LocalDateTime relative) {
    return resolveAdjustedExpression(
        new AdjustedExpression(expression.getScope()), relative.plus(expression.getDuration()));
  }
}
