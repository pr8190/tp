package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Adds a tag to a resident in the hall ledger.
 * The resident is identified using the index number from the displayed resident list.
 * Existing tags will be overwritten by the newly provided tags.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Adds a tag to the resident in the hall ledger"
            + "by the index number used in the displayed resident list."
            + "Existing tags will be overwritten by the input tags.\n"
            + "Parameters: INDEX (must be a positive integer)"
            + "[" + PREFIX_TAG_YEAR + "YEAR]"
            + "[" + PREFIX_TAG_MAJOR + "MAJOR]"
            + "[" + PREFIX_TAG_GENDER + "GENDER]"
            + "Example: " + COMMAND_WORD + "1"
            + PREFIX_TAG_YEAR + "2"
            + PREFIX_TAG_MAJOR + "CS";

    public static final String TAG_SUCCESS = "Added Tag to Resident: %1$s";
    public static final String TAG_NOT_ADDED = "At least one tag (year / major / gender) must be provided.";

    public final Index index;
    public final Set<Tag> tags;

    /**
     * @param index of the person in the filtered person list to edit
     * @param tags list of tags to add to the person
     */
    public TagCommand(Index index, Set<Tag> tags) {
        requireNonNull(index);
        requireNonNull(tags);
        this.index = index;
        this.tags = tags;
    }

    /**
     * Executes the tag command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return a {@code CommandResult} describing the result of the command execution.
     */
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToTag = lastShownList.get(index.getZeroBased());
        Set<Tag> updatedTags = new HashSet<>(personToTag.getTags());
        updatedTags.addAll(tags);
        checkTagLimits(updatedTags);

        Person taggedPerson = new Person(
                personToTag.getName(),
                personToTag.getPhone(),
                personToTag.getEmail(),
                personToTag.getStudentId(),
                personToTag.getRoomNumber(),
                personToTag.getEmergencyContact(),
                updatedTags
        );

        model.setPerson(personToTag, taggedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(TAG_SUCCESS, taggedPerson));
    }

    private void checkTagLimits(Set<Tag> updatedTags) throws CommandException {
        for (TagType type : TagType.values()) {
            long countOfTagsPerType = updatedTags.stream()
                    .filter(tag -> tag.getTagType() == type)
                    .count();
            if (countOfTagsPerType > type.getMaxTagsPerType()) {
                throw new CommandException(
                        "Tag type " + type + " allows at most " + type.getMaxTagsPerType() + " tag(s) per person."
                );
            }
        }
    }
}
