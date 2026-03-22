package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_CONTACT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROOM_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_hasPreamble_failure() {
        // non-empty preamble -> invalid format
        assertParseFailure(parser, "1 " + STUDENTID_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingParts_failure() {
        // has studentID but no other fields specified -> not edited error
        assertParseFailure(parser, STUDENTID_DESC_AMY, EditCommand.MESSAGE_NOT_EDITED);

        // has no studentID but has other fields specified -> invalid format
        assertParseFailure(parser, EMERGENCY_CONTACT_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no studentId and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidTargetStudentId_failure() {
        // invalid studentId format
        assertParseFailure(parser, "INVALID_ID" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, VALID_STUDENTID_AMY + " i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, STUDENTID_DESC_AMY + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, STUDENTID_DESC_AMY + INVALID_PHONE_DESC,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, STUDENTID_DESC_AMY + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        // invalid studentId in field
        assertParseFailure(parser, STUDENTID_DESC_AMY + INVALID_STUDENTID_DESC,
                StudentId.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, STUDENTID_DESC_AMY + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, STUDENTID_DESC_AMY + INVALID_NAME_DESC + INVALID_EMAIL_DESC,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        StudentId targetStudentId = new StudentId(VALID_STUDENTID_AMY);
        String userInput = STUDENTID_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + NAME_DESC_AMY + STUDENTID_DESC_AMY
                + ROOM_NUMBER_DESC_AMY + EMERGENCY_CONTACT_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .withStudentId(VALID_STUDENTID_AMY)
                .withRoomNumber(VALID_ROOM_NUMBER_AMY)
                .withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY)
                .build();

        EditCommand expectedCommand = new EditCommand(targetStudentId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        StudentId targetStudentId = new StudentId(VALID_STUDENTID_AMY);
        String userInput = STUDENTID_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetStudentId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        StudentId targetStudentId = new StudentId(VALID_STUDENTID_AMY);

        // name
        String userInput = STUDENTID_DESC_AMY + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = STUDENTID_DESC_AMY + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = STUDENTID_DESC_AMY + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // studentId
        userInput = STUDENTID_DESC_AMY + STUDENTID_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withStudentId(VALID_STUDENTID_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // roomNumber
        userInput = STUDENTID_DESC_AMY + ROOM_NUMBER_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withRoomNumber(VALID_ROOM_NUMBER_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // emergencyContact
        userInput = STUDENTID_DESC_AMY + EMERGENCY_CONTACT_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder()
                .withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        String userInput = STUDENTID_DESC_AMY + INVALID_PHONE_DESC + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = STUDENTID_DESC_AMY + PHONE_DESC_BOB + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid non-student-ID fields repeated
        userInput = STUDENTID_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_YEAR + PHONE_DESC_AMY
                + PHONE_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + EMERGENCY_CONTACT_DESC_AMY;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // multiple invalid non-student-ID fields repeated
        userInput = STUDENTID_DESC_AMY + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // more than two student-ID fields repeated
        userInput = STUDENTID_DESC_AMY + STUDENTID_DESC_BOB + STUDENTID_DESC_AMY;
        assertParseFailure(parser, userInput,
                String.format(EditCommand.DUPLICATE_STUDENT_ID_PREFIX, EditCommand.MESSAGE_USAGE));

        // more than two student-ID fields repeated, and multiple non-ID fields repeated
        // -> only student-ID duplicate error is captured
        userInput = STUDENTID_DESC_AMY + STUDENTID_DESC_BOB + STUDENTID_DESC_AMY + PHONE_DESC_AMY + PHONE_DESC_BOB;
        assertParseFailure(parser, userInput,
                String.format(EditCommand.DUPLICATE_STUDENT_ID_PREFIX, EditCommand.MESSAGE_USAGE));
    }
}
