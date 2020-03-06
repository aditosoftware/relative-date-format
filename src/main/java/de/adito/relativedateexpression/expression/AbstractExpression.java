package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.expression.exception.ExpressionValidationException;
import de.adito.relativedateexpression.token.ExpressionTokenContainer;
import de.adito.relativedateexpression.token.IExpressionToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents an abstract {@link IExpression} with provides some convenience methods to work with
 * tokens. This expects a {@link ExpressionTokenContainer} within the constructor which will later
 * on be validated through {@link AbstractExpression#validateContainer(ExpressionTokenContainer)}.
 */
public abstract class AbstractExpression implements IExpression {
  private Type type;
  private ExpressionTokenContainer container;

  public AbstractExpression(Type type, ExpressionTokenContainer container) {
    this.type = type;
    this.container = container;

    // Validate the given container.
    validateContainer(container);
  }

  @NotNull
  @Override
  public Type getType() {
    return type;
  }

  @NotNull
  @Override
  public ExpressionTokenContainer getTokenContainer() {
    return container;
  }

  @Override
  public String toString() {
    return getTokenContainer().getAllTokens().stream()
        .map(it -> it.getTokenName() + "=" + it.getValue())
        .collect(Collectors.joining(";"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AbstractExpression)) return false;
    AbstractExpression that = (AbstractExpression) o;
    return getType() == that.getType() && Objects.equals(container, that.container);
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType(), container);
  }

  /**
   * Will resolve the value of the given token with the ExpressionTokenContainer of this expression.
   *
   * @param tokenClass The token to search for.
   * @param <T> The type of the value of the token.
   * @return The value of the token within this expression.
   */
  @Nullable
  protected <T> T getValue(Class<? extends IExpressionToken<T>> tokenClass) {
    IExpressionToken<T> expToken = container.getToken(tokenClass);

    if (expToken == null) return null;

    return expToken.getValue();
  }

  /**
   * Will validate the {@link ExpressionTokenContainer}. This may only throw {@link
   * ExpressionValidationException}.
   *
   * @param container The container to validate.
   * @throws ExpressionValidationException If the validation of this container fails.
   */
  abstract void validateContainer(ExpressionTokenContainer container);
}
