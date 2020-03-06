package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.token.ExpressionTokenContainer;
import de.adito.relativedateexpression.token.ScopeToken;

public class AdjustedExpression extends AbstractExpression implements IExpression {

  public AdjustedExpression(ExpressionTokenContainer container) {
    super(Type.ADJUSTED, container);
  }

  public ScopeToken.Scope getScope() {
    return getValue(ScopeToken.class);
  }

  @Override
  void validateContainer(ExpressionTokenContainer container) {
    ExpressionValidator.requireToken(container, ScopeToken.class);
  }
}
