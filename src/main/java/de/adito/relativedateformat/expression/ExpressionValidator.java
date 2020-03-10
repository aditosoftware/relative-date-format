package de.adito.relativedateformat.expression;

import de.adito.relativedateformat.expression.exception.ExpressionValidationException;
import de.adito.relativedateformat.token.ExpressionTokenContainer;
import de.adito.relativedateformat.token.IExpressionToken;
import de.adito.relativedateformat.tokenizer.exception.TokenizeException;

class ExpressionValidator {
  private ExpressionValidator() {}

  /**
   * Will check if the given token is available on the given {@link ExpressionTokenContainer}.
   *
   * @param container The container to search on.
   * @param token The token for which shall be searched on the container.
   * @throws ExpressionValidationException If the token could not be found on the container.
   */
  static void requireToken(
      ExpressionTokenContainer container, Class<? extends IExpressionToken<?>> token) {
    if (!container.hasToken(token)) throw tokenNotFoundException(token);
  }

  /**
   * Will create a new {@link TokenizeException} which tells that a required token was not
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
