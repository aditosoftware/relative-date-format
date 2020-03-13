package de.adito.relativedateformat.value;

import de.adito.relativedateformat.token.ETokenType;
import de.adito.relativedateformat.token.RelToken;
import de.adito.relativedateformat.token.UnitToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Period;

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
    assertEquals(UnitToken.Unit.DAY, parser.parseValue(ETokenType.UNIT, "DAY"));
    assertEquals(UnitToken.Unit.YEAR, parser.parseValue(ETokenType.UNIT, "YEAR"));
  }

  @Test
  void shouldParseRelValue() throws ValueParseException {
    assertEquals(RelToken.Type.MIXED, parser.parseValue(ETokenType.REL, "MIXED"));
    assertEquals(RelToken.Type.FIXED, parser.parseValue(ETokenType.REL, "FIXED"));
  }

  @Test
  void shouldParsePeriodValue () throws ValueParseException {
    Period d = Period.ofYears(20).plus(Period.ofWeeks(30));

    assertEquals(d, parser.parseValue(ETokenType.PERIOD, d.toString()));
    assertEquals(d, parser.parseValue(ETokenType.START, d.toString()));
    assertEquals(d, parser.parseValue(ETokenType.END, d.toString()));
  }

  @Test
  void shouldThrowExceptionOnInvalidValue() {
    assertThrows(
        ValueParseException.class, () -> parser.parseValue(ETokenType.PERIOD, "ASDF!INVALID"));
  }
}
