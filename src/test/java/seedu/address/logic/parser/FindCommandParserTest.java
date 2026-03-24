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
    public void parse_validArgs_returnsFindCommand() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(Set.of("Alice", "Bob"));
        FindCommand expectedFindCommand = new FindCommand(filterDetails);
        assertParseSuccess(parser, "n=Alice n=Bob n= Swiss Cheese", expectedFindCommand);
    }

    @Test
    public void parse_preamble_throwsParseException() {
        assertParseFailure(parser, "Alice Bob", String.format(MESSAGE_INVALID_COMMAND_FORMAT + "\n"
                + FindCommand.MESSAGE_USAGE));
    }
}
