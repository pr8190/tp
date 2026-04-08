package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.HashMap;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.person.StudentId;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private Prefix[] knownPrefixes = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_STUDENT_ID,
        PREFIX_ROOM_NUMBER, PREFIX_EMERGENCY_CONTACT};

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ParserUtil.checkForUnknownPrefixes(args, AddCommand.MESSAGE_USAGE, knownPrefixes);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, knownPrefixes);

        if (!argMultimap.arePrefixesPresent(knownPrefixes) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(knownPrefixes);

        Person person = createNewPerson(argMultimap);
        return new AddCommand(person);
    }

    private Person createNewPerson(ArgumentMultimap argMultimap) throws ParseException {
        assert argMultimap != null;

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
        RoomNumber roomNumber = ParserUtil.parseRoomNumber(argMultimap.getValue(PREFIX_ROOM_NUMBER).get());
        EmergencyContact emergencyContact = ParserUtil.parseEmergencyContact(argMultimap
                .getValue(PREFIX_EMERGENCY_CONTACT).get());

        Person person = new Person(name, phone, email, studentId, roomNumber, emergencyContact,
                new Remark(""), new HashMap<>());

        return person;
    }
}
