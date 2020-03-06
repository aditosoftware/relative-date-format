package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.expression.exception.ExpressionValidationException;
import de.adito.relativedateexpression.token.DurationToken;
import de.adito.relativedateexpression.token.ExpressionTokenContainer;
import de.adito.relativedateexpression.token.ScopeToken;

import java.time.Duration;

public class MixedExpression extends AbstractExpression implements IExpression {
  public MixedExpression(ExpressionTokenContainer container) {
    super(Type.MIXED, container);
  }

  public Duration getDuration() {
    return getValue(DurationToken.class);
  }

  public ScopeToken.Scope getScope() {
    return getValue(ScopeToken.class);
  }

  @Override
  void validateContainer(ExpressionTokenContainer container) throws ExpressionValidationException {
    ExpressionValidator.requireToken(container, DurationToken.class);
    ExpressionValidator.requireToken(container, ScopeToken.class);
  }
}
