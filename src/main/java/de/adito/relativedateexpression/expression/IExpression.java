package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.token.ExpressionTokenContainer;
import de.adito.relativedateexpression.token.IExpressionToken;
import org.jetbrains.annotations.NotNull;

/** Describes a expression which can hold a type and a {@link ExpressionTokenContainer}. */
public interface IExpression {
  /**
   * Returns the type of this expression.
   *
   * @return Type of expression.
   */
  @NotNull
  Type getType();

  /**
   * Returns the container which holds all {@link IExpressionToken} which represent this expression.
   *
   * @return The container with all {@link IExpressionToken}.
   */
  @NotNull
  ExpressionTokenContainer getTokenContainer();

  enum Type {
    ADJUSTED,
    FIXED,
    MIXED
  }
}
