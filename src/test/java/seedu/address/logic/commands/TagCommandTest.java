package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;
import seedu.address.model.util.PersonBuilder;

public class TagCommandTest {

    private static final StudentId VALID_STUDENT_ID = new StudentId("A1234567X");
    private static final StudentId UNKNOWN_STUDENT_ID = new StudentId("A9999999N");

    private ModelManager model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }


    @Test
    public void constructor_nullStudentId_throwsNullPointerException() {
        Map<TagType, Tag> tags = new HashMap<>();
        tags.put(TagType.YEAR, new Tag(TagType.YEAR, "2"));
        assertThrows(NullPointerException.class, () -> new TagCommand(null, tags));
    }

    @Test
    public void constructor_nullTags_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TagCommand(VALID_STUDENT_ID, null));
    }

    @Test
    public void execute_validStudentIdSingleTag_tagSuccessful() throws Exception {
        Person person = new PersonBuilder().withStudentId(VALID_STUDENT_ID.toString()).build();
        model.addPerson(person);

        Map<TagType, Tag> tags = new HashMap<>();
        tags.put(TagType.YEAR, new Tag(TagType.YEAR, "2"));
        new TagCommand(VALID_STUDENT_ID, tags).execute(model);

        Person taggedPerson = model.getFilteredPersonList().get(0);
        assertEquals(new Tag(TagType.YEAR, "2"), taggedPerson.getTags().get(TagType.YEAR));
    }

    @Test
    public void execute_validStudentIdMultipleTags_allTagsApplied() throws Exception {
        Person person = new PersonBuilder().withStudentId(VALID_STUDENT_ID.toString()).build();
        model.addPerson(person);

        Map<TagType, Tag> tags = new HashMap<>();
        tags.put(TagType.YEAR, new Tag(TagType.YEAR, "2"));
        tags.put(TagType.MAJOR, new Tag(TagType.MAJOR, "CS"));
        tags.put(TagType.GENDER, new Tag(TagType.GENDER, "he/him"));
        new TagCommand(VALID_STUDENT_ID, tags).execute(model);

        Map<TagType, Tag> resultTags = model.getFilteredPersonList().get(0).getTags();
        assertEquals(new Tag(TagType.YEAR, "2"), resultTags.get(TagType.YEAR));
        assertEquals(new Tag(TagType.MAJOR, "CS"), resultTags.get(TagType.MAJOR));
        assertEquals(new Tag(TagType.GENDER, "He/Him"), resultTags.get(TagType.GENDER));
    }

    @Test
    public void execute_existingTagOverwritten_tagUpdated() throws Exception {
        Person person = new PersonBuilder()
                .withStudentId(VALID_STUDENT_ID.toString())
                .withTags(new Object[]{TagType.YEAR, "1"})
                .build();
        model.addPerson(person);

        Map<TagType, Tag> newTags = new HashMap<>();
        newTags.put(TagType.YEAR, new Tag(TagType.YEAR, "3"));
        new TagCommand(VALID_STUDENT_ID, newTags).execute(model);

        assertEquals(new Tag(TagType.YEAR, "3"),
                model.getFilteredPersonList().get(0).getTags().get(TagType.YEAR));
    }

    @Test
    public void execute_newTagMergedWithExisting_existingTagPreserved() throws Exception {
        Person person = new PersonBuilder()
                .withStudentId(VALID_STUDENT_ID.toString())
                .withTags(new Object[]{TagType.YEAR, "1"})
                .build();
        model.addPerson(person);

        Map<TagType, Tag> newTags = new HashMap<>();
        newTags.put(TagType.MAJOR, new Tag(TagType.MAJOR, "CS"));
        new TagCommand(VALID_STUDENT_ID, newTags).execute(model);

        Map<TagType, Tag> resultTags = model.getFilteredPersonList().get(0).getTags();
        assertEquals(new Tag(TagType.YEAR, "1"), resultTags.get(TagType.YEAR));
        assertEquals(new Tag(TagType.MAJOR, "CS"), resultTags.get(TagType.MAJOR));
    }

    @Test
    public void execute_unknownStudentId_throwsCommandException() {
        Person person = new PersonBuilder().withStudentId(VALID_STUDENT_ID.toString()).build();
        model.addPerson(person);

        Map<TagType, Tag> tags = new HashMap<>();
        tags.put(TagType.YEAR, new Tag(TagType.YEAR, "2"));
        TagCommand tagCommand = new TagCommand(UNKNOWN_STUDENT_ID, tags);

        assertThrows(CommandException.class, () -> tagCommand.execute(model));
    }

    @Test
    public void execute_emptyList_throwsCommandException() {
        Map<TagType, Tag> tags = new HashMap<>();
        tags.put(TagType.YEAR, new Tag(TagType.YEAR, "2"));
        TagCommand tagCommand = new TagCommand(VALID_STUDENT_ID, tags);

        assertThrows(CommandException.class, () -> tagCommand.execute(model));
    }


    @Test
    public void execute_validTag_returnsCorrectSuccessMessage() throws Exception {
        Person person = new PersonBuilder().withStudentId(VALID_STUDENT_ID.toString()).build();
        model.addPerson(person);

        Map<TagType, Tag> tags = new HashMap<>();
        tags.put(TagType.YEAR, new Tag(TagType.YEAR, "2"));
        CommandResult result = new TagCommand(VALID_STUDENT_ID, tags).execute(model);

        assertTrue(result.getFeedbackToUser().startsWith("Added Tag to Resident"));
    }
}
