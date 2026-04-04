package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;

import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Adds a tag to a resident in the address book.
 * Existing tags will be overwritten by the newly provided tags.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the resident in the hall ledger "
            + "by the student ID used in the displayed resident list. "
            + "Existing tags will be overwritten by the input tags.\n"
            + "Parameters: i=STUDENT_ID "
            + "[" + PREFIX_TAG_YEAR + "YEAR] "
            + "[" + PREFIX_TAG_MAJOR + "MAJOR] "
            + "[" + PREFIX_TAG_GENDER + "GENDER]\n"
            + "Example: " + COMMAND_WORD + " i=A1234567X "
            + PREFIX_TAG_YEAR + "2 "
            + PREFIX_TAG_MAJOR + "CS "
            + PREFIX_TAG_GENDER + "he/him";

    public static final String MESSAGE_SUCCESS = "Added Tag to Resident: %1$s";
    public static final String MESSAGE_TAG_NOT_ADDED =
            "At least one tag must be provided.";

    private final StudentId studentId;
    private final Map<TagType, Tag> tags;

    /**
     * Creates a {@code TagCommand} to add the specified {@code tags}
     * to the resident identified by the given {@code StudentId}.
     */
    public TagCommand(StudentId studentId, Map<TagType, Tag> tags) {
        requireNonNull(studentId);
        requireNonNull(tags);

        if (tags.isEmpty()) {
            throw new IllegalArgumentException(MESSAGE_TAG_NOT_ADDED);
        }

        this.studentId = studentId;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToTag = model.getPersonByStudentId(studentId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentId)));

        Person taggedPerson = createTaggedPerson(personToTag, tags);

        model.showAllPersons();
        model.setPerson(personToTag, taggedPerson);
        model.setSelectedPerson(taggedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(taggedPerson)));
    }

    /**
     * Creates and returns a new {@code Person} with the specified tags.
     * All other fields remain unchanged.
     *
     * @param personToTag the original resident whose details are to be copied.
     * @param tags the new tags to assign to the resident.
     * @return a new {@code Person} instance with the updated tags.
     */
    private static Person createTaggedPerson(Person personToTag, Map<TagType, Tag> tags) {
        HashMap<TagType, Tag> updatedTags = new HashMap<>(personToTag.getTags());
        updatedTags.putAll(tags);

        return new Person(
                personToTag.getName(),
                personToTag.getPhone(),
                personToTag.getEmail(),
                personToTag.getStudentId(),
                personToTag.getRoomNumber(),
                personToTag.getEmergencyContact(),
                personToTag.getRemark(),
                updatedTags,
                personToTag.getDemeritIncidents()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand otherTagCommand)) {
            return false;
        }

        return studentId.equals(otherTagCommand.studentId)
                && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("tags", tags)
                .toString();
    }
}
