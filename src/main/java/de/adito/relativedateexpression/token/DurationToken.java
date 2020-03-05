package de.adito.relativedateexpression.token;

import java.time.Duration;

public class DurationToken implements IDurationExpressionToken {
  private Duration duration;

  public DurationToken(Duration duration) {
    this.duration = duration;
  }

  @Override
  public String getTokenName() {
    return "DURATION";
  }

  @Override
  public Duration getValue() {
    return duration;
  }
}
