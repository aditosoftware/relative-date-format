package de.adito.relativedateexpression.expression;

import java.time.Duration;

public class FixedExpression implements IExpression {
  private Duration start;
  private Duration end;

  public FixedExpression (Duration start, Duration end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public Type getType () {
    return Type.FIXED;
  }

  public Duration getStart() {
    return start;
  }

  public Duration getEnd() {
    return end;
  }
}
