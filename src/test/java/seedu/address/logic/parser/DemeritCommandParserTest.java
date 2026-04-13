package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DemeritCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;

public class DemeritCommandParserTest {

    private final DemeritCommandParser parser = new DemeritCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        DemeritCommand expectedCommand =
                new DemeritCommand(new StudentId("A1234567X"), 18, "Stayed over");

        assertEquals(expectedCommand, parser.parse(" i=A1234567X di=18 rm=Stayed over"));
    }

    @Test
    public void parse_withoutRemark_success() throws Exception {
        DemeritCommand expectedCommand =
                new DemeritCommand(new StudentId("A1234567X"), 18, "");

        assertEquals(expectedCommand, parser.parse(" i=A1234567X di=18"));
    }

    @Test
    public void parse_missingStudentId_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" di=18 rm=Stayed over"));
    }

    @Test
    public void parse_missingRuleIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i=A1234567X rm=Stayed over"));
    }

    @Test
    public void parse_invalidStudentId_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i=INVALID di=18"));
    }

    @Test
    public void parse_nonNumericRuleIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i=A1234567X di=abc"));
    }

    @Test
    public void parse_zeroRuleIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i=A1234567X di=0"));
    }

    @Test
    public void parse_negativeRuleIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i=A1234567X di=-1"));
    }

    @Test
    public void parse_withPreamble_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" hello i=A1234567X di=18"));
    }

    @Test
    public void parse_duplicateStudentIdPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i=A1234567X i=A7654321X di=18"));
    }

    @Test
    public void parse_duplicateDemeritIndexPrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" i=A1234567X di=18 di=1"));
    }

    @Test
    public void parse_duplicateStudentIdAndDemeritIndexPrefixes_throwsParseException() {
        assertThrows(ParseException.class, () ->
                parser.parse(" i=A1234567X i=A7654321X di=18 di=1"));
    }
}