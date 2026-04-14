package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.TagCommand;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

public class TagCommandParserTest {

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_singleTag_success() {
        Map<TagType, Tag> expectedTags = new HashMap<>();
        expectedTags.put(TagType.YEAR, new Tag(TagType.YEAR, "2"));

        assertParseSuccess(parser, " i=A1234567Y y=2",
                new TagCommand(new StudentId(VALID_STUDENTID_AMY), expectedTags));
    }

    @Test
    public void parse_multipleTags_success() {
        Map<TagType, Tag> expectedTags = new HashMap<>();
        expectedTags.put(TagType.YEAR, new Tag(TagType.YEAR, "3"));
        expectedTags.put(TagType.MAJOR, new Tag(TagType.MAJOR, "Business"));

        assertParseSuccess(parser, " i=A8765432Y y=3 m=Business",
                new TagCommand(new StudentId(VALID_STUDENTID_BOB), expectedTags));
    }

    @Test
    public void parse_genderNormalization_success() {
        Map<TagType, Tag> expectedTags = new HashMap<>();
        expectedTags.put(TagType.GENDER, new Tag(TagType.GENDER, "she/her"));

        // Test various gender inputs that should normalize to "she/her"
        assertParseSuccess(parser, " i=A1234567Y g=she",
                new TagCommand(new StudentId(VALID_STUDENTID_AMY), expectedTags));
        assertParseSuccess(parser, " i=A1234567Y g=her",
                new TagCommand(new StudentId(VALID_STUDENTID_AMY), expectedTags));
        assertParseSuccess(parser, " i=A1234567Y g=she/her",
                new TagCommand(new StudentId(VALID_STUDENTID_AMY), expectedTags));
    }

    @Test
    public void parse_emptyTagValue_removesTag() {
        Map<TagType, Tag> expectedTags = new HashMap<>();
        expectedTags.put(TagType.YEAR, null); // null indicates removal

        assertParseSuccess(parser, " i=A1234567Y y=",
                new TagCommand(new StudentId(VALID_STUDENTID_AMY), expectedTags));
    }

    @Test
    public void parse_repeatedValues_failure() {
        String validExpectedPersonString = " i=A1234567Y y=1";

        // EP: multiple student IDs
        assertParseFailure(parser, STUDENTID_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_ID));

        // EP: multiple years
        assertParseFailure(parser, " i=A1234567Y y=1 y=2",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TAG_YEAR));

        // EP: multiple majors
        assertParseFailure(parser, " i=A1234567Y m=CS m=Math",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TAG_MAJOR));

        // EP: multiple genders
        assertParseFailure(parser, " i=A1234567Y g=he/him g=she/her",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TAG_GENDER));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

        // missing student ID prefix
        assertParseFailure(parser, " y=1", expectedMessage);

        // missing preamble but has student ID
        assertParseFailure(parser, " preamble i=A1234567Y y=1", expectedMessage);
    }

    @Test
    public void parse_noTagsProvided_failure() {
        assertParseFailure(parser, " i=A1234567Y", TagCommand.MESSAGE_TAG_NOT_ADDED);
    }

    @Test
    public void parse_invalidStudentId_failure() {
        assertParseFailure(parser, " i=invalid y=1", StudentId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidTagValues_failure() {
        // invalid year
        assertParseFailure(parser, " i=A1234567Y y=7", Tag.MESSAGE_CONSTRAINTS);

        // invalid gender
        assertParseFailure(parser, " i=A1234567Y g=invalid", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidGender_failure() {
        assertParseFailure(parser, " i=A1234567Y g=invalidgender", Tag.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_unknownPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_UNKNOWN_PREFIX, "x=") + "\n" + TagCommand.MESSAGE_USAGE;
        assertParseFailure(parser, " i=A1234567Y y=1 x=unknown", expectedMessage);
    }
}
