package de.adito.relativedateexpression.value;

import de.adito.relativedateexpression.token.ETokenType;
import de.adito.relativedateexpression.token.RelToken;
import de.adito.relativedateexpression.token.ScopeToken;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.function.Function;

public class DefaultValueParser implements IValueParser {
  @Override
  public Object parseValue(ETokenType type, String value) throws ValueParseException {
    switch (type) {
      case SCOPE:
        return parseEnum(ETokenType.SCOPE, ScopeToken.Scope::valueOf, value);
      case REL:
        return parseEnum(ETokenType.REL, RelToken.Type::valueOf, value);
      case END:
      case START:
      case DURATION:
        return parseDuration(type, value);
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
  private Duration parseDuration(ETokenType type, String value) throws ValueParseException {
    if (value == null || value.isEmpty() || value.equalsIgnoreCase("null")) return Duration.ZERO;

    try {
      return Duration.parse(value);
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
  private Object parseEnum(ETokenType type, Function<String, Object> accessor, String value)
      throws ValueParseException {
    try {
      return accessor.apply(value);
    } catch (Exception exception) {
      throw valueParseException(value, type);
    }
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
