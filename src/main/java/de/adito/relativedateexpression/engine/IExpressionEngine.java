package de.adito.relativedateexpression.engine;

import de.adito.relativedateexpression.expression.IExpression;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * Describes a engine which capable of resolving a {@link IExpression} into a {@link IResult} based
 * on a given relative date ({@link LocalDateTime}).
 */
public interface IExpressionEngine {
  /**
   * Will resolve the given expression into a time frame relative on the given LocalDateTime.
   *
   * @param expression The expression which describes the time frame.
   * @param relative The date which shall be used to calculate the time frame.
   * @return The result of the calculation.
   * @throws ExpressionCalculateException If the expression could not be resolved.
   */
  IResult resolve(@NotNull IExpression expression, @NotNull LocalDateTime relative)
      throws ExpressionCalculateException;

  /**
   * Will resolve the given expression into a time frame relative on the given LocalDateTime. This
   * will resolve based on the current time ({@link LocalDateTime#now()}).
   *
   * @param expression The expression which describes the time frame.
   * @return The result of the calculation.
   * @throws ExpressionCalculateException If the expression could not be resolved.
   */
  default IResult resolve(@NotNull IExpression expression) throws ExpressionCalculateException {
    return resolve(expression, LocalDateTime.now());
  }
}
