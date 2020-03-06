package de.adito.relativedateexpression.token;

import java.time.Duration;

public class EndToken extends AbstractToken<Duration> implements IDurationExpressionToken {
  public EndToken (Duration value) {
    super("END", value);
  }
}
