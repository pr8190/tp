package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Deletes a resident identified by StudentId from HallLedger.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the resident identified by student ID from HallLedger.\n"
            + "Format: " + COMMAND_WORD + " i=STUDENT_ID\n"
            + "Example: " + COMMAND_WORD + " i=A1234567X";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted resident: %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "ResidentNotFound: No resident found with student ID %s.";

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

        Person personToDelete = model.getPersonByStudentId(targetStudentId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, targetStudentId)));

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
