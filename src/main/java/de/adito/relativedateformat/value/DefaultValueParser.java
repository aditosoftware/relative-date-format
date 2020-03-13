package de.adito.relativedateformat.value;

import de.adito.relativedateformat.token.ETokenType;
import de.adito.relativedateformat.token.RelToken;
import de.adito.relativedateformat.token.UnitToken;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Period;
import java.util.function.Function;

public class DefaultValueParser implements ValueParser {
  @Override
  public Object parseValue(ETokenType type, String value) {
    switch (type) {
      case UNIT:
        return parseEnum(ETokenType.UNIT, UnitToken.Unit::valueOf, value);
      case REL:
        return parseEnum(ETokenType.REL, RelToken.Type::valueOf, value);
      case END:
      case START:
      case PERIOD:
        return parsePeriod(type, value);
      case FULL:
        return parseBoolean(type, value);
      default:
        return null;
    }
  }

  /**
   * Will parse the given string value into an instance of a {@link Duration}.
   *
   * @param value The value to parse as string
   * @return The parsed duration.
   * @throws ValueParseException If the parsing of the value fails.
   */
  private Duration parseDuration(ETokenType type, String value) {
    if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) return Duration.ZERO;

    try {
      return Duration.parse(value);
    } catch (Exception exception) {
      throw valueParseException(value, type);
    }
  }

  /**
   * Will parse the given string value into an instance of a {@link Period}.
   *
   * @param type The token which is being parsed.
   * @param value The value to parse as string.
   * @return The parsed period.
   * @throws ValueParseException If the parsing of the value fails.
   */
  private Period parsePeriod(ETokenType type, String value) {
    if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) return Period.ZERO;

    try {
      return Period.parse(value);
    } catch (Exception exception) {
      throw valueParseException(value, type);
    }
  }

  /**
   * Will parse a value into an enum. The enum value will be resolved through the given accessor
   * function.
   *
   * @param type The type which is currently being resolved.
   * @param accessor The accessor for the enum. (e.g. {@code ScopeToken.Scope::valueOf})
   * @param value The value to parse a string.
   * @return The parsed enum value.
   * @throws ValueParseException If the given string value could not be parsed into the enum.
   */
  @NotNull
  private Object parseEnum(ETokenType type, Function<String, Object> accessor, String value) {
    try {
      return accessor.apply(value);
    } catch (Exception exception) {
      throw valueParseException(value, type);
    }
  }

  /**
   * Will parse the given value into an integer. If the given string is no valid integer it will
   * throw an exception.
   *
   * @param value The value to parse into an integer.
   * @return The parsed integer value
   * @throws ValueParseException If the given string value could not be parsed into an integer.
   */
  private Object parseInteger(ETokenType type, String value) {
    try {
      return Integer.valueOf(value);
    } catch (Exception exception) {
      throw valueParseException(value, type);
    }
  }

  /**
   * Will parse the given value into a boolean. If the given string is no valid boolean it will
   * throw an exception.
   *
   * @param type The type of the token which is being parsed.
   * @param value The string value to parse.
   * @return The parsed boolean value.
   * @throws ValueParseException If the given string value could not be parsed into an boolean.
   */
  private Object parseBoolean(ETokenType type, String value) {
    if (value.equalsIgnoreCase("true")) return true;
    if (value.equalsIgnoreCase("false")) return false;

    throw valueParseException(value, type);
  }

  /**
   * Will create a new instance of a {@link ValueParseException} which tells that the given
   * inputValue is invalid for the given tokenType
   *
   * @param inputValue The input value which is invalid.
   * @param tokenType The token type which has been parsed.
   * @return The created exception.
   */
  private ValueParseException valueParseException(String inputValue, ETokenType tokenType) {
    return new ValueParseException(
        String.format("Value '%s' is invalid for token type '%s'", inputValue, tokenType.name()));
  }
}
