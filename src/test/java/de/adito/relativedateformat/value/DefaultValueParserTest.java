package de.adito.relativedateformat.value;

import de.adito.relativedateformat.token.ETokenType;
import de.adito.relativedateformat.token.RelToken;
import de.adito.relativedateformat.token.ScopeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultValueParserTest {
  private DefaultValueParser parser;

  @BeforeEach
  void init() {
    parser = new DefaultValueParser();
  }

  @Test
  void shouldParseScopeValue() throws ValueParseException {
    assertEquals(ScopeToken.Scope.DAY, parser.parseValue(ETokenType.SCOPE, "DAY"));
    assertEquals(ScopeToken.Scope.YEAR, parser.parseValue(ETokenType.SCOPE, "YEAR"));
  }

  @Test
  void shouldParseRelValue() throws ValueParseException {
    assertEquals(RelToken.Type.MIXED, parser.parseValue(ETokenType.REL, "MIXED"));
    assertEquals(RelToken.Type.FIXED, parser.parseValue(ETokenType.REL, "FIXED"));
  }

  @Test
  void shouldParseDurationValue() throws ValueParseException {
    Duration d = Duration.ofDays(1).plus(Duration.ofMinutes(360));

    assertEquals(d, parser.parseValue(ETokenType.DURATION, d.toString()));
    assertEquals(d, parser.parseValue(ETokenType.START, d.toString()));
    assertEquals(d, parser.parseValue(ETokenType.END, d.toString()));
  }

  @Test
  void shouldThrowExceptionOnInvalidValue() {
    assertThrows(
        ValueParseException.class, () -> parser.parseValue(ETokenType.DURATION, "ASDF!INVALID"));
  }
}
