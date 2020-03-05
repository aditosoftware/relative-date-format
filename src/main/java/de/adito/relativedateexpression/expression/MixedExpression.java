package de.adito.relativedateexpression.expression;

import de.adito.relativedateexpression.token.ScopeToken;

import java.time.Duration;

public class MixedExpression implements IExpression {
  private ScopeToken.Scope scope;
  private Duration duration;

  public MixedExpression (ScopeToken.Scope scope, Duration duration) {
    this.scope = scope;
    this.duration = duration;
  }

  @Override
  public Type getType () {
    return Type.MIXED;
  }

  public Duration getDuration () {
    return duration;
  }

  public ScopeToken.Scope getScope () {
    return scope;
  }
}
