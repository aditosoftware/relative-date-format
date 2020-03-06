package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.expression.exception.ExpressionValidationException;
import de.adito.relativedateexpression.parser.ExpressionParseException;
import de.adito.relativedateexpression.token.ExpressionTokenContainer;
import de.adito.relativedateexpression.token.IExpressionToken;
import de.adito.relativedateexpression.token.ScopeToken;

class ExpressionValidator {

  /**
   * Will check if the given token is available on the given {@link ExpressionTokenContainer}.
   *
   * @param container The container to search on.
   * @param token The token for which shall be searched on the container.
   * @throws ExpressionValidationException If the token could not be found on the container.
   */
  static void requireToken(
      ExpressionTokenContainer container, Class<? extends IExpressionToken<?>> token)
      throws ExpressionValidationException {
    if (!container.hasToken(ScopeToken.class)) throw tokenNotFoundException(ScopeToken.class);
  }

  /**
   * Will create a new {@link ExpressionParseException} which tells that a required token was not
   * found on the expression.
   *
   * @param token The missing token.
   * @return The created exception instance.
   */
  private static ExpressionValidationException tokenNotFoundException(
      Class<? extends IExpressionToken<?>> token) {
    return new ExpressionValidationException(
        String.format("Required token '%s' was not found", token.getSimpleName()));
  }
}
