package de.adito.relativedateexpression.token;

import java.time.Duration;

public class EndToken implements IDurationExpressionToken {
  private Duration value;

  public EndToken(Duration value) {
    this.value = value;
  }

  @Override
  public String getTokenName() {
    return "END";
  }

  @Override
  public Duration getValue() {
    return value;
  }
}
