package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;
import seedu.address.model.person.StudentId;



/**
 * Parses input arguments and creates a {@link seedu.address.logic.commands.RemarkCommand} object.
 *
 * <p>Requires {@code PREFIX_STUDENT_ID} and {@code PREFIX_REMARK}.
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    private static final Prefix[] ALL_PREFIXES = {
        PREFIX_STUDENT_ID,
        PREFIX_REMARK
    };

    /**
     * Parses the given {@code args} and returns a {@link RemarkCommand} for execution.
     *
     * @param args the raw argument string;
     * @throws ParseException if the input is missing a student ID or contains unknown
     *                        or duplicate prefixes
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ParserUtil.checkForUnknownPrefixes(args, MESSAGE_USAGE, ALL_PREFIXES);

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);

        if (argumentMultimap.getValue(PREFIX_STUDENT_ID).isEmpty() || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(ALL_PREFIXES);

        StudentId studentId = ParserUtil.parseStudentId(argumentMultimap.getValue(PREFIX_STUDENT_ID).get());
        Remark remark = new Remark(argumentMultimap.getValue(PREFIX_REMARK).orElse(""));

        return new RemarkCommand(studentId, remark);
    }
}
