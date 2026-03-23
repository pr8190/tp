package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.StudentId;

public class RemarkCommand extends Command{

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_ARGUMENTS = "StudentId: %s, Remark: %s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a remark to the selected person.\n"
            + "Parameters: i=STUDENT_ID r=REMARK\n"
            + "Example: " + COMMAND_WORD + " i=A1234567Z r=Is vegetarian";

    public static final String NOT_IMPLEMENTED_MESSAGE = "This command is not implemented yet.";

    private final StudentId studentId;
    private final String remark;

    public RemarkCommand(StudentId studentId, String remark) {
        this.studentId = studentId;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
       throw new CommandException(String.format(studentId.toString(), remark));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand otherRemarkCommand = (RemarkCommand) other;
        return studentId.equals(otherRemarkCommand.studentId)
                && remark.equals(otherRemarkCommand.remark);
    }


}
