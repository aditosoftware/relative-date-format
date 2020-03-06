package de.adito.relativedateexpression.token;

import java.util.*;

/**
 * Represents a simple container which can hold multiple {@link IExpressionToken}. Only one instance
 * per token can exist within this container.
 */
public class ExpressionTokenContainer {
  @SuppressWarnings("rawtypes")
  private Map<Class<? extends IExpressionToken>, IExpressionToken<?>> tokens;

  public ExpressionTokenContainer() {
    tokens = new LinkedHashMap<>();
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
    tokens.put(token.getClass(), token);
  }

  /**
   * Will return all registered {@link IExpressionToken} within this container. The returned
   * collection is immutable.
   *
   * @return A immutable collection of all registered tokens within this container.
   */
  @SuppressWarnings("rawtypes")
  public Collection<IExpressionToken> getAllTokens() {
    return Collections.unmodifiableCollection(tokens.values());
  }
}
