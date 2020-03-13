package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;

import java.time.Period;

public class PeriodToken extends AbstractToken<Period> implements IPeriodExpressionToken {
  public PeriodToken(@NotNull Period value) {
    super("PERIOD", value);
  }
}
