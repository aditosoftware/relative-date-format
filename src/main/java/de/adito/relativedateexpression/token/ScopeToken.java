package de.adito.relativedateexpression.token;

public class ScopeToken extends AbstractToken<ScopeToken.Scope> {
  public ScopeToken(Scope value) {
    super("SCOPE", value);
  }

  public enum Scope {
    YEAR,
    MONTH,
    WEEK,
    DAY
  }
}
