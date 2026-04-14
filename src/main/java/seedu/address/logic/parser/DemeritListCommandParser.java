package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DemeritListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DemeritListCommand} object.
 */
public class DemeritListCommandParser implements Parser<DemeritListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DemeritListCommand
     * and returns a DemeritListCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DemeritListCommand parse(String args) throws ParseException {
        if (!args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemeritListCommand.MESSAGE_USAGE));
        }

        return new DemeritListCommand();
    }
}

