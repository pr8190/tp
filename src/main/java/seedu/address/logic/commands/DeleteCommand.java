package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.util.ModelUtil.getPersonByStudentIdOrThrow;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a resident identified by StudentId from Hall Ledger.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the resident identified by student ID from Hall Ledger.\n"
            + "Parameters: i=STUDENT_ID\n"
            + "Example: " + COMMAND_WORD + " i=A1234567X";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted resident: %1$s";

    private final StudentId targetStudentId;

    /**
     * Constructor to initialize studentId
     *
     * @param targetStudentId the studentId of the student to be deleted
     */
    public DeleteCommand(StudentId targetStudentId) {
        requireNonNull(targetStudentId);
        this.targetStudentId = targetStudentId;
    }

    /**
     * Returns the student ID targeted for deletion.
     */
    public StudentId getTargetStudentId() {
        return targetStudentId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = getPersonByStudentIdOrThrow(model, targetStudentId);

        model.deletePerson(personToDelete);
        model.showAllPersons();

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand otherDeleteCommand)) {
            return false;
        }

        return targetStudentId.equals(otherDeleteCommand.targetStudentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", targetStudentId)
                .toString();
    }
}
