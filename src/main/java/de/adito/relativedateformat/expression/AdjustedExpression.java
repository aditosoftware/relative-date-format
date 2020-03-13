package de.adito.relativedateformat.expression;

import de.adito.relativedateformat.token.ExpressionTokenContainer;
import de.adito.relativedateformat.token.UnitToken;

public class AdjustedExpression extends AbstractExpression implements IExpression {
  public AdjustedExpression(ExpressionTokenContainer container) {
    super(Type.ADJUSTED, container);
  }

  public UnitToken.Unit getUnit() {
    return getValue(UnitToken.class);
  }

  @Override
  void validateContainer(ExpressionTokenContainer container) {
    ExpressionValidator.requireToken(container, UnitToken.class);
  }
}
