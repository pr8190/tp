package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_PERSON_NOT_FOUND;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.StudentId;

/**
 * Adds a remark to a resident identified using unique StudentId.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a remark to the selected person.\n"
            + "Parameters: i=STUDENT_ID rm=REMARK\n"
            + "Example: " + COMMAND_WORD
            + " i=A1234567Z rm=Is vegetarian";

    public static final String MESSAGE_SUCCESS = "Added Remark to Resident: %1$s";

    private final StudentId studentId;
    private final Remark remark;

    /**
     * Creates a {@code RemarkCommand} to add the specified {@code Remark}
     * to the resident identified by the given {@code StudentId}.
     */
    public RemarkCommand(StudentId studentId, Remark remark) {
        requireNonNull(studentId);
        requireNonNull(remark);

        this.studentId = studentId;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToRemark = model.getPersonByStudentId(studentId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentId)));

        Person editedPerson = createEditedPerson(personToRemark, remark);

        model.setPerson(personToRemark, editedPerson);
        model.setSelectedPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a new {@code Person} with the specified remark.
     * All other fields remain unchanged.
     *
     * @param personToRemark the original resident whose details are to be copied.
     * @param remark the new remark to assign to the resident.
     * @return a new {@code Person} instance with the updated remark.
     */
    private static Person createEditedPerson(Person personToRemark, Remark remark) {
        return new Person(
                personToRemark.getName(),
                personToRemark.getPhone(),
                personToRemark.getEmail(),
                personToRemark.getStudentId(),
                personToRemark.getRoomNumber(),
                personToRemark.getEmergencyContact(),
                remark, // Replace the remark field with the new remark
                personToRemark.getTags()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemarkCommand otherRemarkCommand)) {
            return false;
        }

        return studentId.equals(otherRemarkCommand.studentId)
                && remark.equals(otherRemarkCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("studentId", studentId)
                .add("remark", remark)
                .toString();
    }
}
