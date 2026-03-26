package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_ARGUMENT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.FilterDetails;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_EMPTY_ARGUMENT + "\n"
                + FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "x=Alice", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(Set.of("Alice", "Bob", "Swiss Cheese"));
        filterDetails.setEmailKeywords(Set.of("alice@example.com"));
        filterDetails.setPhoneNumberKeywords(Set.of("91234567"));
        filterDetails.setRoomNumberKeywords(Set.of("10A"));
        filterDetails.setStudentIdKeywords(Set.of("A1234567X"));
        filterDetails.setEmergencyContactKeywords(Set.of("87654321"));
        filterDetails.setTagYearKeywords(Set.of("Y1"));
        filterDetails.setTagMajorKeywords(Set.of("CS", "Math"));
        filterDetails.setTagGenderKeywords(Set.of("Female"));

        FindCommand expectedFindCommand = new FindCommand(filterDetails);
        assertParseSuccess(parser,
                " n=Alice n=Bob n=Swiss Cheese e=alice@example.com p=91234567 r=10A i=A1234567X "
                        + "ec=87654321 y=Y1 m=CS m=Math g=Female",
                expectedFindCommand);
    }

    @Test
    public void parse_preamble_throwsParseException() {
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_moreThan10ValuesForSinglePrefix_throwsParseException() {
        StringBuilder userInput = new StringBuilder();
        for (int index = 1; index <= 11; index++) {
            userInput.append(" n=Name").append(index);
        }

        String expectedMessage = String.format(
                FilterDetails.MESSAGE_TOO_MANY_PREFIX_VALUES, "[n=]", FilterDetails.MAX_VALUES_PER_PREFIX);
        assertParseFailure(parser, userInput.toString(), expectedMessage);
    }

    @Test
    public void parse_exactly10ValuesForSinglePrefix_success() {
        StringBuilder userInput = new StringBuilder();
        for (int index = 1; index <= 10; index++) {
            userInput.append(" n=Name").append(index);
        }

        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(Set.of(
                "Name1", "Name2", "Name3", "Name4", "Name5",
                "Name6", "Name7", "Name8", "Name9", "Name10"));

        assertParseSuccess(parser, userInput.toString(), new FindCommand(filterDetails));
    }
}
