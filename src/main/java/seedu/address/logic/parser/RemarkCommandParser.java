package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_PREFIX;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a RemarkCommand object.
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code args} and returns a {@link RemarkCommand} for execution.
     *
     * @param args the raw argument string; must not be {@code null}
     * @throws ParseException if the input is missing a student ID or contains unknown
     *                        or duplicate prefixes
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        checkForUnknownPrefixes(args);

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args,
                        CliSyntax.PREFIX_STUDENT_ID,
                        CliSyntax.PREFIX_REMARK);

        if (argumentMultimap.getValue(PREFIX_STUDENT_ID).isEmpty() || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_ID, PREFIX_REMARK);

        StudentId studentId = ParserUtil.parseStudentId(argumentMultimap.getValue(PREFIX_STUDENT_ID).get());
        Remark remark = new Remark(argumentMultimap.getValue(CliSyntax.PREFIX_REMARK).orElse(""));

        return new RemarkCommand(studentId, remark);
    }

    private void checkForUnknownPrefixes(String args) throws ParseException {
        String unknownPrefix = ArgumentTokenizer.checkForUnknownPrefixes(args, CliSyntax.PREFIX_STUDENT_ID,
                CliSyntax.PREFIX_REMARK);

        if (!unknownPrefix.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_PREFIX, unknownPrefix)
                    + "\n" + RemarkCommand.MESSAGE_USAGE);
        }
    }
}
