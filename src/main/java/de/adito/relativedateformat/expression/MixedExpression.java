package de.adito.relativedateformat.expression;

import de.adito.relativedateformat.token.PeriodToken;
import de.adito.relativedateformat.token.ExpressionTokenContainer;
import de.adito.relativedateformat.token.UnitToken;
import org.jetbrains.annotations.Nullable;

import java.time.Period;

public class MixedExpression extends AbstractExpression implements IExpression {
  public MixedExpression(ExpressionTokenContainer container) {
    super(Type.MIXED, container);
  }

  public @Nullable Period getPeriod () {
    return getValue(PeriodToken.class);
  }

  public UnitToken.Unit getScope() {
    return getValue(UnitToken.class);
  }

  @Override
  void validateContainer(ExpressionTokenContainer container) {
    ExpressionValidator.requireToken(container, PeriodToken.class);
    ExpressionValidator.requireToken(container, UnitToken.class);
  }
}
