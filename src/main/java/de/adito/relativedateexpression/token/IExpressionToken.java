package de.adito.relativedateexpression.token;

/**
 * Describes a token which exists on the expression with the given {@link this#getValue()}.
 *
 * @param <T> The type of the value of this token.
 */
public interface IExpressionToken<T> {
  /**
   * Will return the name of the token (e.g. "DURATION").
   *
   * @return The name of the token.
   */
  String getTokenName();

  /**
   * Will return the value of this token.
   *
   * @return The value of this token.
   */
  T getValue();
}
