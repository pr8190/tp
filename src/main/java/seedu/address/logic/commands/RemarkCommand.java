package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_PERSON_NOT_FOUND;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.StudentId;

/**
 * Adds a remark to a resident in the hall ledger.
 * The resident is identified using the index number from the displayed resident list.
 * Existing remarks will be overwritten by the newly provided remark.
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
    private final String remark;

    /**
     * @param studentId of the person in the filtered person list to edit
     * @param remark remark to add to the person
     */
    public RemarkCommand(StudentId studentId, String remark) {
        this.studentId = studentId;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToRemark = model.getPersonByStudentId(studentId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, studentId)));

        Remark newRemark = new Remark(remark);

        Person remarkedPerson = createRemarkedPerson(personToRemark, newRemark);

        model.setPerson(personToRemark, remarkedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(remarkedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code remarkedPerson}
     */
    public static Person createRemarkedPerson(Person personToRemark, Remark remark) {
        return new Person(
                personToRemark.getName(),
                personToRemark.getPhone(),
                personToRemark.getEmail(),
                personToRemark.getStudentId(),
                personToRemark.getRoomNumber(),
                personToRemark.getEmergencyContact(),
                remark,
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
}
