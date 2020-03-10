package de.adito.relativedateformat.expression;

import de.adito.relativedateformat.token.DurationToken;
import de.adito.relativedateformat.token.ExpressionTokenContainer;
import de.adito.relativedateformat.token.ScopeToken;

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
  void validateContainer(ExpressionTokenContainer container) {
    ExpressionValidator.requireToken(container, DurationToken.class);
    ExpressionValidator.requireToken(container, ScopeToken.class);
  }
}
