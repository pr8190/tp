package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DemeritListCommand;

/**
 * Contains integration tests for {@code DemeritListCommandParser}.
 */
public class DemeritListCommandParserTest {

    private final DemeritListCommandParser parser = new DemeritListCommandParser();

    @Test
    public void parse_noArguments_success() {
        assertParseSuccess(parser, "", new DemeritListCommand());
        assertParseSuccess(parser, "   ", new DemeritListCommand());
    }

    @Test
    public void parse_withTrailingArguments_throwsParseException() {
        assertParseFailure(parser, " x",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemeritListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " nonsense",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemeritListCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " rm=abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemeritListCommand.MESSAGE_USAGE));
    }
}
