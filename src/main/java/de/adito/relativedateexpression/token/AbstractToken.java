package de.adito.relativedateexpression.token;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a abstract {@link IExpressionToken} which fully implements the interface and accepts
 * the parameters through the constructor.
 *
 * @param <T> The type of the value of the token.
 */
public abstract class AbstractToken<T> implements IExpressionToken<T> {
  private final String tokenName;
  private final T value;

  public AbstractToken(String tokenName, T value) {
    this.tokenName = tokenName;
    this.value = value;
  }

  @NotNull
  @Override
  public String getTokenName() {
    return tokenName;
  }

  @NotNull
  @Override
  public T getValue() {
    return value;
  }
}
