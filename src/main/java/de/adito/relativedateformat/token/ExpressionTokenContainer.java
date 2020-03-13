package de.adito.relativedateformat.token;

import java.util.*;

/**
 * Represents a simple container which can hold multiple {@link IExpressionToken}. Only one instance
 * per token can exist within this container.
 */
public class ExpressionTokenContainer {
  @SuppressWarnings("rawtypes")
  private final Map<Class<? extends IExpressionToken>, IExpressionToken<?>> tokens;

  private final Set<IExpressionToken<?>> tokensSet;

  public ExpressionTokenContainer() {
    tokens = new LinkedHashMap<>();
    tokensSet = new LinkedHashSet<>();
  }

  /**
   * Will check if the given token exists on this container.
   *
   * @param tokenClass The token to search for on this container.
   * @return If the container has an instance of the given token
   */
  public boolean hasToken(Class<? extends IExpressionToken<?>> tokenClass) {
    return tokens.containsKey(tokenClass);
  }

  /**
   * Will return the instance of the given token class. If no instance of the given token exists on
   * this container it will return `null`.
   *
   * @param tokenClass The token to return.
   * @param <T> The type of the value of the token.
   * @return The {@link IExpressionToken} instance.
   */
  @SuppressWarnings("unchecked")
  public <T extends IExpressionToken<?>> T getToken(Class<T> tokenClass) {
    return (T) tokens.get(tokenClass);
  }

  /**
   * Will add the given token to the container. If another instance of tht token already exists on
   * this container, the older one will be overridden.
   *
   * @param token The token to add to the container.
   */
  public void addToken(IExpressionToken<?> token) {
    if(token.getValue() == null)
      return;

    tokens.put(token.getClass(), token);
    tokensSet.add(token);
  }

  /**
   * Will return all registered {@link IExpressionToken} within this container. The returned list is
   * immutable, which means it can not be modified.
   *
   * @return A immutable list o all registered tokens within this container.
   */
  @SuppressWarnings("rawtypes")
  public List<IExpressionToken> getAllTokens() {
    return List.copyOf(tokensSet);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ExpressionTokenContainer)) return false;
    ExpressionTokenContainer that = (ExpressionTokenContainer) o;

    return tokensSet.containsAll(that.tokensSet) && that.tokensSet.containsAll(tokensSet);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tokensSet);
  }
}
