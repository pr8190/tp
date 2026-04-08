package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.util.ModelUtil.getPersonByStudentIdOrThrow;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;

import java.util.Collections;
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
 * Adds a tag to a resident identified using unique StudentId.
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

    public static final String MESSAGE_SUCCESS = "Updated Tag for Resident: %1$s";
    public static final String MESSAGE_TAG_NOT_ADDED =
            "At least one tag must be provided.";

    private final StudentId targetStudentId;
    private final Map<TagType, Tag> tags;

    /**
     * Creates a {@code TagCommand} to add the specified {@code tags}
     * to the resident identified by the given {@code StudentId}.
     */
    public TagCommand(StudentId studentId, Map<TagType, Tag> tags) {
        requireNonNull(studentId);
        requireNonNull(tags);

        assert !tags.isEmpty() : "Tags should not be empty (should be caught by parser)";

        this.targetStudentId = studentId;
        this.tags = Collections.unmodifiableMap(tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToTag = getPersonByStudentIdOrThrow(model, targetStudentId);
        Person taggedPerson = createTaggedPerson(personToTag, tags);

        model.setPerson(personToTag, taggedPerson);
        model.showAllPersons();
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
        Map<TagType, Tag> updatedTags = computeUpdatedTags(personToTag.getTags(), tags);
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

    /**
     * Computes the updated tags by merging the existing tags with the new tags.
     *
     * @param existingTags the existing tags of the resident.
     * @param newTags the new tags to be added or updated. Tags with null values indicate removal of that tag type.
     * @return a new map containing the merged tags, where new tags overwrite existing tags of the same type,
     *      and tags with null values in newTags are removed from the resulting map.
     */
    private static Map<TagType, Tag> computeUpdatedTags(Map<TagType, Tag> existingTags, Map<TagType, Tag> newTags) {

        HashMap<TagType, Tag> updatedTags = new HashMap<>(existingTags);
        newTags.forEach((type, tag) -> {
            if (tag == null) {
                updatedTags.remove(type); // Remove the tag if the new tag value is null
            } else {
                updatedTags.put(type, tag);
            }
        });

        assert updatedTags.size() <= TagType.values().length : "Cannot exceed number of TagTypes";
        return updatedTags;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagCommand otherTagCommand)) {
            return false;
        }

        return targetStudentId.equals(otherTagCommand.targetStudentId)
                && tags.equals(otherTagCommand.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", targetStudentId)
                .add("tags", tags)
                .toString();
    }
}
