package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.token.ScopeToken;

public class AdjustedExpression implements IExpression {
  private ScopeToken.Scope scope;

  public AdjustedExpression (ScopeToken.Scope scope) {
    this.scope = scope;
  }

  @Override
  public Type getType () {
    return Type.ADJUSTED;
  }

  public ScopeToken.Scope getScope () {
    return scope;
  }
}
