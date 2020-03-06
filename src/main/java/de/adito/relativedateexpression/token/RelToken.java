package de.adito.relativedateexpression.token;

public class RelToken extends AbstractToken<RelToken.Type> {
  public RelToken(Type value) {
    super("REL", value);
  }

  public enum Type {
    ADJUSTED,
    FIXED,
    MIXED
  }
}
