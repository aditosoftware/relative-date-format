package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;

import java.time.Period;

public class StartToken extends AbstractToken<Period> implements IPeriodExpressionToken {
  public StartToken(@NotNull Period value) {
    super("START", value);
  }
}
