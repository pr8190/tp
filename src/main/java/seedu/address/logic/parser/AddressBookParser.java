package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DemeritCommand;
import seedu.address.logic.commands.DemeritListCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT =
            Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level
        // (i.e., FINE, FINER and lower) log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        if (commandWord.equals(AddCommand.COMMAND_WORD)) {
            return new AddCommandParser().parse(arguments);
        } else if (commandWord.equals(EditCommand.COMMAND_WORD)) {
            return new EditCommandParser().parse(arguments);
        } else if (commandWord.equals(DeleteCommand.COMMAND_WORD)) {
            return new DeleteCommandParser().parse(arguments);
        } else if (commandWord.equals(ClearCommand.COMMAND_WORD)) {
            return new ClearCommand();
        } else if (commandWord.equals(FindCommand.COMMAND_WORD)) {
            return new FindCommandParser().parse(arguments);
        } else if (commandWord.equals(ListCommand.COMMAND_WORD)) {
            return new ListCommand();
        } else if (commandWord.equals(ExitCommand.COMMAND_WORD)) {
            return new ExitCommand();
        } else if (commandWord.equals(HelpCommand.COMMAND_WORD)) {
            return new HelpCommand();
        } else if (commandWord.equals(TagCommand.COMMAND_WORD)) {
            return new TagCommandParser().parse(arguments);
        } else if (commandWord.equals(RemarkCommand.COMMAND_WORD)) {
            return new RemarkCommandParser().parse(arguments);
        } else if (commandWord.equals(DemeritListCommand.COMMAND_WORD)) {
            return new DemeritListCommand();
        } else if (commandWord.equals(DemeritCommand.COMMAND_WORD)) {
            return new DemeritCommandParser().parse(arguments);
        } else {
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
