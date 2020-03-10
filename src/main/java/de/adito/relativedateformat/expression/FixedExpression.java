package de.adito.relativedateformat.expression;

import de.adito.relativedateformat.expression.exception.ExpressionValidationException;
import de.adito.relativedateformat.token.EndToken;
import de.adito.relativedateformat.token.ExpressionTokenContainer;
import de.adito.relativedateformat.token.StartToken;

import java.time.Duration;

public class FixedExpression extends AbstractExpression implements IExpression {

  public FixedExpression(ExpressionTokenContainer container) {
    super(Type.FIXED, container);
  }

  public Duration getStart() {
    return getValue(StartToken.class);
  }

  public Duration getEnd() {
    return getValue(EndToken.class);
  }

  @Override
  void validateContainer(ExpressionTokenContainer container) {
    if (!container.hasToken(StartToken.class) && container.hasToken(EndToken.class))
      throw new ExpressionValidationException("Either token 'START' or 'END' must be set");
  }
}
