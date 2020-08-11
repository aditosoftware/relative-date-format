package de.adito.relativedateformat.engine;

import de.adito.relativedateformat.engine.exception.EngineException;
import de.adito.relativedateformat.expression.IExpression;
import org.jetbrains.annotations.*;

import java.time.LocalDateTime;

/**
 * Describes a engine which capable of resolving a {@link IExpression} into a {@link
 * RelativeDateResult} based on a given relative date ({@link LocalDateTime}).
 */
public interface RelativeDateEngine {
  /**
   * Will resolve the given expression into a time frame relative on the given LocalDateTime.
   *
   * @param expression The expression which describes the time frame.
   * @param relative The date which shall be used to calculate the time frame.
   * @param pProperties The configuration properties to use.
   * @return The result of the calculation.
   * @throws EngineException If the expression could not be resolved.
   */
  RelativeDateResult resolve(
      @NotNull IExpression expression,
      @NotNull LocalDateTime relative,
      @Nullable RelativeDateEngineProperties pProperties);

  /**
   * Will resolve the given expression into a time frame relative on the given LocalDateTime. This
   * will resolve based on the current time ({@link LocalDateTime#now()}).
   *
   * @param expression The expression which describes the time frame.
   * @param pProperties The configuration properties to use.
   * @return The result of the calculation.
   * @throws EngineException If the expression could not be resolved.
   */
  default RelativeDateResult resolve(
      @NotNull IExpression expression, @Nullable RelativeDateEngineProperties pProperties) {
    return resolve(expression, LocalDateTime.now(), pProperties);
  }

  /**
   * Will resolve the given expression into a time frame relative on the given LocalDateTime. This
   * will resolve based on the current time ({@link LocalDateTime#now()}).
   *
   * @param expression The expression which describes the time frame.
   * @return The result of the calculation.
   * @throws EngineException If the expression could not be resolved.
   */
  default RelativeDateResult resolve(
      @NotNull IExpression expression, @NotNull LocalDateTime relative) {
    return resolve(expression, relative, null);
  }

  /**
   * Will resolve the given expression into a time frame relative to the current time. This will
   * utilize {@link LocalDateTime#now()} to access the current time.
   *
   * @param expression The expression which describes the time frame.
   * @return The result of the calculation.
   * @throws EngineException If the expression could not be resolved.
   */
  default RelativeDateResult resolve(@NotNull IExpression expression) {
    return resolve(expression, (RelativeDateEngineProperties) null);
  }

  /**
   * Will create a new {@link RelativeDateEngine} instance.
   *
   * @return new RelativeDateEngine.
   */
  static RelativeDateEngine get() {
    return new DefaultRelativeDateEngine();
  }
}
