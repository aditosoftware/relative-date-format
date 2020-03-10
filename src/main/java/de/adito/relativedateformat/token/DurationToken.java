package de.adito.relativedateformat.token;

import java.time.Duration;

public class DurationToken extends AbstractToken<Duration> implements IDurationExpressionToken {
  public DurationToken(Duration value) {
    super("DURATION", value);
  }
}
