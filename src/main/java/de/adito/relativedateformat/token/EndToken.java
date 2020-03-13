package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;

import java.time.Period;

public class EndToken extends AbstractToken<Period> implements IPeriodExpressionToken {
  public EndToken(@NotNull Period value) {
    super("END", value);
  }
}
