package de.adito.relativedateformat.factory;

import de.adito.relativedateformat.expression.AdjustedExpression;
import de.adito.relativedateformat.expression.FixedExpression;
import de.adito.relativedateformat.expression.MixedExpression;
import de.adito.relativedateformat.token.*;
import org.jetbrains.annotations.Nullable;

import java.time.Period;

/** Represents a factory for all three available expression types. */
public class RelativeDateExpressionFactory {
  private RelativeDateExpressionFactory() {}

  /**
   * Will create a new {@link AdjustedExpression} with the given scope.
   *
   * @param unit The scope for the expression.
   * @return The new expression.
   */
  public static AdjustedExpression adjusted(UnitToken.Unit unit) {
    ExpressionTokenContainer container = new ExpressionTokenContainer();
    container.addToken(new RelToken(RelToken.Type.ADJUSTED));
    if (unit != null) container.addToken(new UnitToken(unit));

    return new AdjustedExpression(container);
  }

  /**
   * Will create a new {@link FixedExpression} with the given start and end date.
   *
   * @param start The start token for the expression.
   * @param end The end token for the expression.
   * @param full The full token for the expression.
   * @return The new expression.
   */
  public static FixedExpression fixed(
      @Nullable Period start, @Nullable Period end, @Nullable Boolean full) {
    ExpressionTokenContainer container = new ExpressionTokenContainer();
    container.addToken(new RelToken(RelToken.Type.FIXED));

    if (start != null) container.addToken(new StartToken(start));
    if (end != null) container.addToken(new EndToken(end));
    if (full != null) container.addToken(new FullToken(full));

    return new FixedExpression(container);
  }

  /**
   * Will create a new {@link MixedExpression} with the given duration and scope.
   *
   * @param period The period for the expression.
   * @param unit The start for the expression.
   * @return The new expression.
   */
  public static MixedExpression mixed(Period period, UnitToken.Unit unit) {
    ExpressionTokenContainer container = new ExpressionTokenContainer();
    container.addToken(new RelToken(RelToken.Type.MIXED));
    if (period != null) container.addToken(new PeriodToken(period));
    if (unit != null) container.addToken(new UnitToken(unit));

    return new MixedExpression(container);
  }
}
