package de.adito.relativedateexpression.token;

public class RelToken implements IExpressionToken<RelToken.Type> {
  private Type value;

  public RelToken(Type value) {
    this.value = value;
  }

  @Override
  public String getTokenName() {
    return "REL";
  }

  @Override
  public Type getValue() {
    return value;
  }

  public enum Type {
    ADJUSTED,
    FIXED,
    MIXED
  }
}
