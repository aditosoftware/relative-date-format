package de.adito.relativedateexpression.factory;

import de.adito.relativedateexpression.expression.AdjustedExpression;
import de.adito.relativedateexpression.expression.FixedExpression;
import de.adito.relativedateexpression.expression.MixedExpression;
import de.adito.relativedateexpression.token.*;

import java.time.Duration;

/** Represents a factory for all three available expression types. */
public class ExpressionFactory {
  private ExpressionFactory() {}

  /**
   * Will create a new {@link AdjustedExpression} with the given scope.
   *
   * @param scope The scope for the expression.
   * @return The new expression.
   */
  public static AdjustedExpression adjusted(ScopeToken.Scope scope) {
    ExpressionTokenContainer container = new ExpressionTokenContainer();
    container.addToken(new RelToken(RelToken.Type.ADJUSTED));
    container.addToken(new ScopeToken(scope));

    return new AdjustedExpression(container);
  }

  /**
   * Will create a new {@link FixedExpression} with the given start and end date.
   *
   * @param start The start for the expression.
   * @param end The end for the expression.
   * @return The new expression.
   */
  public static FixedExpression fixed(Duration start, Duration end) {
    ExpressionTokenContainer container = new ExpressionTokenContainer();
    container.addToken(new RelToken(RelToken.Type.FIXED));
    container.addToken(new StartToken(start));
    container.addToken(new EndToken(end));

    return new FixedExpression(container);
  }

  /**
   * Will create a new {@link MixedExpression} with the given duration and scope.
   *
   * @param duration The duration for the expression.
   * @param scope The start for the expression.
   * @return The new expression.
   */
  public static MixedExpression mixed(Duration duration, ScopeToken.Scope scope) {
    ExpressionTokenContainer container = new ExpressionTokenContainer();
    container.addToken(new RelToken(RelToken.Type.MIXED));
    container.addToken(new DurationToken(duration));
    container.addToken(new ScopeToken(scope));

    return new MixedExpression(container);
  }
}
