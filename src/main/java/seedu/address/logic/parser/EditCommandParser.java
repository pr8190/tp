package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_PHONE,
                        PREFIX_EMAIL,
                        PREFIX_STUDENT_ID,
                        PREFIX_ROOM_NUMBER,
                        PREFIX_EMERGENCY_CONTACT);

        validatePrefixes(argMultimap);

        // Parse the student ID, throw parsing errors if needed
        String targetStudentIdString = argMultimap.getAllValues(PREFIX_STUDENT_ID).get(0);
        StudentId targetStudentId = ParserUtil.parseStudentId(targetStudentIdString);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        // If there are two student ID prefixes, the second one is the edited student ID
        if (argMultimap.getAllValues(PREFIX_STUDENT_ID).size() == 2) {
            StudentId editedStudentId = ParserUtil.parseStudentId(argMultimap.getAllValues(PREFIX_STUDENT_ID).get(1));
            editPersonDescriptor.setStudentId(editedStudentId);
        }
        if (argMultimap.getValue(PREFIX_ROOM_NUMBER).isPresent()) {
            editPersonDescriptor.setRoomNumber(ParserUtil.parseRoomNumber(argMultimap
                    .getValue(PREFIX_ROOM_NUMBER).get()));
        }
        if (argMultimap.getValue(PREFIX_EMERGENCY_CONTACT).isPresent()) {
            editPersonDescriptor.setEmergencyContact(ParserUtil.parseEmergencyContact(argMultimap
                    .getValue(PREFIX_EMERGENCY_CONTACT).get()));
        }


        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(targetStudentId, editPersonDescriptor);
    }

    private static void validatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        // If there's a preamble -> invalid command format
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        // If no ID prefixes are present -> missing required parameter
        List<String> studentIdValues = argMultimap.getAllValues(PREFIX_STUDENT_ID);
        if (studentIdValues.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        // ArgumentTokenizer doesn't tokenize PREFIX_TAG, so it will be treated as preamble.
        assert argMultimap.getAllValues(PREFIX_TAG).isEmpty() : "PREFIX_TAG should not be tokenized";

        // Check for duplicate student ID prefixes (allow up to 2)
        if (argMultimap.getAllValues(PREFIX_STUDENT_ID).size() > 2) {
            throw new ParseException(String.format(EditCommand.DUPLICATE_STUDENT_ID_PREFIX, EditCommand.MESSAGE_USAGE));
        }

        // Check for duplicate prefixes in single-valued fields
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ROOM_NUMBER, PREFIX_EMERGENCY_CONTACT);
    }
}
