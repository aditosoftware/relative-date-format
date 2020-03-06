package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.expression.exception.ExpressionValidationException;
import de.adito.relativedateexpression.token.EndToken;
import de.adito.relativedateexpression.token.ExpressionTokenContainer;
import de.adito.relativedateexpression.token.StartToken;

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
  void validateContainer(ExpressionTokenContainer container) throws ExpressionValidationException {
    if (!container.hasToken(StartToken.class) && container.hasToken(EndToken.class))
      throw new ExpressionValidationException("Either token 'START' or 'END' must be set");
  }
}
