package de.adito.relativedateformat.token;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.time.Period;
import java.util.stream.Stream;

/**
 * Represents a enum which holds all available {@link IExpressionToken}s. This also provides some
 * utility methods for the tokens.
 */
@SuppressWarnings("rawtypes")
public enum ETokenType {
  UNIT(UnitToken.class, UnitToken.Unit.class),
  END(EndToken.class, Period.class),
  START(StartToken.class, Period.class),
  REL(RelToken.class, RelToken.Type.class),
  PERIOD(PeriodToken.class, Period.class),
  FULL(FullToken.class, Boolean.class);

  private final Class<? extends IExpressionToken> token;
  private final Class<?> value;

  <T> ETokenType(Class<? extends IExpressionToken<T>> token, Class<T> value) {
    this.token = token;
    this.value = value;
  }

  /**
   * Will search for a {@link ETokenType} with the given name. The value will be compared using the
   * value of {@link Enum#name()}.
   *
   * @param input The name to search for.
   * @return The found token or `null` it not found.
   */
  @Nullable
  public static ETokenType searchToken(@NotNull String input) {
    return Stream.of(ETokenType.values())
        .filter(it -> it.toString().equalsIgnoreCase(input))
        .findFirst()
        .orElse(null);
  }

  /**
   * Will create a new instance of the given {@link ETokenType}. The class for the token is defined
   * through {@link ETokenType#token}. This will also check if the class of the given value is
   * compatible with the expected value class.
   *
   * @param type The type of the token for which the instance shall be created.
   * @param value The value for the token as {@link Object}.
   * @return The created instance
   */
  @NotNull
  public IExpressionToken createInstance(@NotNull ETokenType type, @NotNull Object value) {
    // Check if the class of the given value is compatible with the expected value class
    if (!type.value.isAssignableFrom(value.getClass()))
      throw new IllegalArgumentException(
          String.format(
              "Value of type '%s' is not assignable to expected type '%s'",
              value.getClass().getName(), type.value.getName()));

    try {
      // Create a new instance of the token.
      return type.token.getConstructor(type.value).newInstance(value);
    } catch (InstantiationException
        | IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException ignored) {
      // Throw a generic exception if the instantiation of the token class fails.
      throw new IllegalStateException(
          String.format(
              "Unable to create instance of '%s' with value of type '%s'",
              type.token.getName(), value.getClass().getName()));
    }
  }
}
