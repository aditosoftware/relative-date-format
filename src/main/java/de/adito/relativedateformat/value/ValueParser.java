package de.adito.relativedateformat.value;

import de.adito.relativedateformat.token.ETokenType;

/** Describes a parser for token values. */
public interface ValueParser {
  /**
   * Will parse the given value into the expected value of the given type.
   *
   * @param type The type which will be parsed.
   * @param value The value to parse.
   * @return The parsed value as object.
   * @throws ValueParseException If the parsing of the value fails.
   */
  Object parseValue(ETokenType type, String value);
}
