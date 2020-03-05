package de.adito.relativedateexpression.expression;

public interface IExpression {
  Type getType ();

  enum Type {
    ADJUSTED,
    FIXED,
    MIXED
  }
}
