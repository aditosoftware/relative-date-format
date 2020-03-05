package de.adito.relativedateexpression.token;

import java.time.Duration;

public class StartToken implements IDurationExpressionToken {
  private Duration value;

  public StartToken(Duration value) {
    this.value = value;
  }

  @Override
  public String getTokenName() {
    return "START";
  }

  @Override
  public Duration getValue() {
    return value;
  }
}
