package de.adito.relativedateformat.tokenizer;

import de.adito.relativedateformat.expression.AdjustedExpression;
import de.adito.relativedateformat.expression.FixedExpression;
import de.adito.relativedateformat.expression.IExpression;
import de.adito.relativedateformat.expression.MixedExpression;
import de.adito.relativedateformat.token.ETokenType;
import de.adito.relativedateformat.token.ExpressionTokenContainer;
import de.adito.relativedateformat.token.RelToken;
import de.adito.relativedateformat.tokenizer.exception.TokenizeException;
import de.adito.relativedateformat.value.DefaultValueParser;
import de.adito.relativedateformat.value.ValueParser;
import de.adito.relativedateformat.value.ValueParseException;

public class DefaultRelativeDateTokenizer implements RelativeDateTokenizer {
  private final ValueParser valueParser;

  public DefaultRelativeDateTokenizer () {
    this(new DefaultValueParser());
  }

  public DefaultRelativeDateTokenizer (ValueParser valueParser) {
    this.valueParser = valueParser;
  }

  @Override
  public IExpression tokenize(String expression) {
    String[] split = expression.split(";");

    // Create a new empty token container.
    ExpressionTokenContainer tokens = new ExpressionTokenContainer();

    for (String splitPart : split) {
      String[] kvPart = splitPart.split("=");

      // The key/value part has to be exactly of length 2.
      if (kvPart.length != 2)
        throw new TokenizeException(String.format("Invalid part given '%s'", splitPart));

      String key = kvPart[0];
      String value = kvPart[1];

      // Search for the token.
      ETokenType type = ETokenType.searchToken(key);
      if (type == null)
        throw new TokenizeException(String.format("Unknown token '%s' given", key));

      // Parse the value of the token.
      Object parsedValue;
      try {
        parsedValue = valueParser.parseValue(type, value);
      } catch (ValueParseException exception) {
        throw new TokenizeException(exception.getMessage());
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
   * @throws TokenizeException If a required token is missing.
   */
  private IExpression createExpression(ExpressionTokenContainer container)
      throws TokenizeException {
    if (!container.hasToken(RelToken.class))
      throw new TokenizeException("Required token 'REL' not found");

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
   * @throws TokenizeException If anything fails.
   */
  private IExpression createAdjustedExpression(ExpressionTokenContainer container)
      throws TokenizeException {
    return new AdjustedExpression(container);
  }

  /**
   * Will create a new {@link FixedExpression} using the given {@link ExpressionTokenContainer}.
   *
   * @param container The container, which will be used to create the expression instance.
   * @return The created expression instance.
   * @throws TokenizeException If anything fails.
   */
  private IExpression createFixedExpression(ExpressionTokenContainer container)
      throws TokenizeException {
    return new FixedExpression(container);
  }

  /**
   * Will create a new {@link MixedExpression} using the given {@link ExpressionTokenContainer}.
   *
   * @param container The container, which will be used to create the expression instance.
   * @return The created expression instance.
   * @throws TokenizeException If anything fails.
   */
  private IExpression createMixedExpression(ExpressionTokenContainer container)
      throws TokenizeException {

    return new MixedExpression(container);
  }
}
