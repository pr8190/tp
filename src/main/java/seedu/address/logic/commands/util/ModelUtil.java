package seedu.address.logic.commands.util;

import static seedu.address.logic.Messages.MESSAGE_RESIDENT_NOT_FOUND;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;


/**
 * Utility class for Model-related operations in commands.
 */
public class ModelUtil {

    /**
     * Returns the person with the given student ID from the model, or throws a CommandException if not found.
     *
     * @param model the model to search for the person
     * @param studentId the student ID of the person to find
     * @return the person with the given student ID
     * @throws CommandException if no person with the given student ID is found in the model
     */
    public static Person getPersonByStudentIdOrThrow(Model model, StudentId studentId)
            throws CommandException {
        return model.getPersonByStudentId(studentId)
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_RESIDENT_NOT_FOUND, studentId)));
    }
}
