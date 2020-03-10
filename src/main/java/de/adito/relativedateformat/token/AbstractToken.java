package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

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

  @Override
  public boolean equals (Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractToken)) return false;
    AbstractToken<?> that = (AbstractToken<?>) o;
    return Objects.equals(getTokenName(), that.getTokenName()) &&
        Objects.equals(getValue(), that.getValue());
  }

  @Override
  public int hashCode () {
    return Objects.hash(getTokenName(), getValue());
  }
}
