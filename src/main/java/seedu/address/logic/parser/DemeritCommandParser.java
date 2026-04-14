package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEMERIT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.logic.commands.DemeritCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new {@code DemeritCommand} object.
 */
public class DemeritCommandParser implements Parser<DemeritCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DemeritCommand
     * and returns a DemeritCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DemeritCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_ID, PREFIX_DEMERIT_INDEX, PREFIX_REMARK);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_ID, PREFIX_DEMERIT_INDEX);

        if (!argMultimap.getValue(PREFIX_STUDENT_ID).isPresent()
                || !argMultimap.getValue(PREFIX_DEMERIT_INDEX).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemeritCommand.MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
        int ruleIndex = ParserUtil.parseDemeritIndex(argMultimap.getValue(PREFIX_DEMERIT_INDEX).get());
        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new DemeritCommand(studentId, ruleIndex, remark);
    }
}
