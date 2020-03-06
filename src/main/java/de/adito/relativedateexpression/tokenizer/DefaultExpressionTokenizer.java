package de.adito.relativedateexpression.tokenizer;

import de.adito.relativedateexpression.expression.AdjustedExpression;
import de.adito.relativedateexpression.expression.FixedExpression;
import de.adito.relativedateexpression.expression.IExpression;
import de.adito.relativedateexpression.expression.MixedExpression;
import de.adito.relativedateexpression.token.ETokenType;
import de.adito.relativedateexpression.token.ExpressionTokenContainer;
import de.adito.relativedateexpression.token.RelToken;
import de.adito.relativedateexpression.value.DefaultValueParser;
import de.adito.relativedateexpression.value.IValueParser;
import de.adito.relativedateexpression.value.ValueParseException;

public class DefaultExpressionTokenizer implements ExpressionTokenizer {
  private final IValueParser valueParser;

  public DefaultExpressionTokenizer() {
    this(new DefaultValueParser());
  }

  public DefaultExpressionTokenizer(IValueParser valueParser) {
    this.valueParser = valueParser;
  }

  @Override
  public IExpression tokenize(String expression) throws ExpressionTokenizeException {
    String[] split = expression.split(";");

    // Create a new empty token container.
    ExpressionTokenContainer tokens = new ExpressionTokenContainer();

    for (String splitPart : split) {
      String[] kvPart = splitPart.split("=");

      // The key/value part has to be exactly of length 2.
      if (kvPart.length != 2)
        throw new ExpressionTokenizeException(String.format("Invalid part given '%s'", splitPart));

      String key = kvPart[0];
      String value = kvPart[1];

      // Search for the token.
      ETokenType type = ETokenType.searchToken(key);
      if (type == null)
        throw new ExpressionTokenizeException(String.format("Unknown token '%s' given", key));

      // Parse the value of the token.
      Object parsedValue;
      try {
        parsedValue = valueParser.parseValue(type, value);
      } catch (ValueParseException exception) {
        throw new ExpressionTokenizeException(exception.getMessage());
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
   * @throws ExpressionTokenizeException If a required token is missing.
   */
  private IExpression createExpression(ExpressionTokenContainer container)
      throws ExpressionTokenizeException {
    if (!container.hasToken(RelToken.class))
      throw new ExpressionTokenizeException("Required token 'REL' not found");

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
   * @throws ExpressionTokenizeException If anything fails.
   */
  private IExpression createAdjustedExpression(ExpressionTokenContainer container)
      throws ExpressionTokenizeException {
    return new AdjustedExpression(container);
  }

  /**
   * Will create a new {@link FixedExpression} using the given {@link ExpressionTokenContainer}.
   *
   * @param container The container, which will be used to create the expression instance.
   * @return The created expression instance.
   * @throws ExpressionTokenizeException If anything fails.
   */
  private IExpression createFixedExpression(ExpressionTokenContainer container)
      throws ExpressionTokenizeException {
    return new FixedExpression(container);
  }

  /**
   * Will create a new {@link MixedExpression} using the given {@link ExpressionTokenContainer}.
   *
   * @param container The container, which will be used to create the expression instance.
   * @return The created expression instance.
   * @throws ExpressionTokenizeException If anything fails.
   */
  private IExpression createMixedExpression(ExpressionTokenContainer container)
      throws ExpressionTokenizeException {

    return new MixedExpression(container);
  }
}
