package de.adito.relativedateexpression.token;

import java.time.Duration;

public class StartToken extends AbstractToken<Duration> implements IDurationExpressionToken {
  public StartToken(Duration value) {
    super("START", value);
  }
}
