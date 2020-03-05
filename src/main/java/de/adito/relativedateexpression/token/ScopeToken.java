package de.adito.relativedateexpression.token;

public class ScopeToken implements IExpressionToken<ScopeToken.Scope> {
  private Scope value;

  public ScopeToken(Scope value) {
    this.value = value;
  }

  @Override
  public String getTokenName() {
    return "SCOPE";
  }

  @Override
  public Scope getValue() {
    return value;
  }

  public enum Scope {
    YEAR,
    MONTH,
    WEEK,
    DAY
  }
}
