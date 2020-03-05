package de.adito.relativedateexpression.parser;

import de.adito.relativedateexpression.expression.AdjustedExpression;
import de.adito.relativedateexpression.expression.FixedExpression;
import de.adito.relativedateexpression.expression.IExpression;
import de.adito.relativedateexpression.expression.MixedExpression;
import de.adito.relativedateexpression.token.*;
import de.adito.relativedateexpression.value.DefaultValueParser;
import de.adito.relativedateexpression.value.IValueParser;
import de.adito.relativedateexpression.value.ValueParseException;

import java.time.Duration;

public class DefaultExpressionTokenizer implements ExpressionTokenizer {
  private final IValueParser valueParser;

  public DefaultExpressionTokenizer() {
    this(new DefaultValueParser());
  }

  public DefaultExpressionTokenizer(IValueParser valueParser) {
    this.valueParser = valueParser;
  }

  @Override
  public IExpression tokenize(String expression) throws ExpressionParseException {
    String[] split = expression.split(";");

    // Create a new empty token container.
    ExpressionTokenContainer tokens = new ExpressionTokenContainer();

    for (String splitPart : split) {
      String[] kvPart = splitPart.split("=");

      // The key/value part has to be exactly of length 2.
      if (kvPart.length != 2)
        throw new ExpressionParseException(String.format("Invalid part given '%s'", splitPart));

      String key = kvPart[0];
      String value = kvPart[1];

      // Search for the token.
      ETokenType type = ETokenType.searchToken(key);
      if (type == null)
        throw new ExpressionParseException(String.format("Unknown token '%s' given", key));

      // Parse the value of the token.
      Object parsedValue;
      try {
        parsedValue = valueParser.parseValue(type, value);
      } catch (ValueParseException exception) {
        throw new ExpressionParseException(exception.getMessage());
      }

      // Create a new instance of the token and add it to the container.
      tokens.addToken(type.createInstance(type, parsedValue));
    }

    return createExpression(tokens);
  }

  /**
   * Will create a new {@link IExpression} based on the given {@link ExpressionTokenContainer}. The
   * expression to use will be defined through the {@link RelToken} value.
   *
   * @param container The container which contains all tokens to create the expression.
   * @return The expression, never null.
   * @throws ExpressionParseException If a required token is missing.1
   */
  private IExpression createExpression(ExpressionTokenContainer container)
      throws ExpressionParseException {
    if (!container.hasToken(RelToken.class))
      throw new ExpressionParseException("Required token 'REL' not found");

    RelToken relToken = container.getToken(RelToken.class);

    switch (relToken.getValue()) {
      case ADJUSTED:
        return createAdjustedExpression(container);
      case FIXED:
        return createFixedExpression(container);
      case MIXED:
        return createMixedExpression(container);
    }

    return null;
  }

  /**
   * Will create a new {@link AdjustedExpression} using the given {@link ExpressionTokenContainer}.
   *
   * @param container The container, which will be used to create the expression instance.
   * @return The created expression instance.
   * @throws ExpressionParseException If anything fails.
   */
  private IExpression createAdjustedExpression(ExpressionTokenContainer container)
      throws ExpressionParseException {
    requireToken(container, ScopeToken.class);

    ScopeToken scope = container.getToken(ScopeToken.class);

    return new AdjustedExpression(scope.getValue());
  }

  /**
   * Will create a new {@link FixedExpression} using the given {@link ExpressionTokenContainer}.
   *
   * @param container The container, which will be used to create the expression instance.
   * @return The created expression instance.
   * @throws ExpressionParseException If anything fails.
   */
  private IExpression createFixedExpression(ExpressionTokenContainer container)
      throws ExpressionParseException {
    if (!container.hasToken(StartToken.class) && container.hasToken(EndToken.class))
      throw new ExpressionParseException("Either token 'START' or 'END' must be set");

    StartToken start = container.getToken(StartToken.class);
    EndToken end = container.getToken(EndToken.class);

    return new FixedExpression(
        start != null ? start.getValue() : Duration.ZERO,
        end != null ? end.getValue() : Duration.ZERO);
  }

  /**
   * Will create a new {@link MixedExpression} using the given {@link ExpressionTokenContainer}.
   *
   * @param container The container, which will be used to create the expression instance.
   * @return The created expression instance.
   * @throws ExpressionParseException If anything fails.
   */
  private IExpression createMixedExpression(ExpressionTokenContainer container)
      throws ExpressionParseException {
    requireToken(container, DurationToken.class);
    requireToken(container, ScopeToken.class);

    ScopeToken scope = container.getToken(ScopeToken.class);
    DurationToken duration = container.getToken(DurationToken.class);

    return new MixedExpression(scope.getValue(), duration.getValue());
  }

  /**
   * Will check if the given token is available on the given {@link ExpressionTokenContainer}.
   *
   * @param container The container to search on.
   * @param token The token for which shall be searched on the container.
   * @throws ExpressionParseException If the token could not be found on the container.
   */
  private void requireToken(
      ExpressionTokenContainer container, Class<? extends IExpressionToken<?>> token)
      throws ExpressionParseException {
    if (!container.hasToken(ScopeToken.class)) throw tokenNotFoundException(ScopeToken.class);
  }

  /**
   * Will create a new {@link ExpressionParseException} which tells that a required token was not
   * found on the expression.
   *
   * @param token The missing token.
   * @return The created exception instance.
   */
  private ExpressionParseException tokenNotFoundException(
      Class<? extends IExpressionToken<?>> token) {
    return new ExpressionParseException(
        String.format("Required token '%s' was not found", token.getSimpleName()));
  }
}
