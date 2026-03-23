package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;

public class RemarkCommandParser implements Parser<RemarkCommand> {

    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args,
                        CliSyntax.PREFIX_STUDENT_ID,
                        CliSyntax.PREFIX_REMARK);

        if (!argumentMultimap.getValue(PREFIX_STUDENT_ID).isPresent() || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(argumentMultimap.getValue(PREFIX_STUDENT_ID).get());
        String remark = argumentMultimap.getValue(CliSyntax.PREFIX_REMARK).orElse("");

        return new RemarkCommand(studentId, remark);
    }
}
