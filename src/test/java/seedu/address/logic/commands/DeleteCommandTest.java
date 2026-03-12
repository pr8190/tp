package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validStudentIdUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId targetStudentId = personToDelete.getStudentId();
        DeleteCommand deleteCommand = new DeleteCommand(targetStudentId);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIdUnfilteredList_throwsCommandException() {
        StudentId nonExistentStudentId = new StudentId("A9999999Z");
        DeleteCommand deleteCommand = new DeleteCommand(nonExistentStudentId);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND, nonExistentStudentId));
    }

    @Test
    public void execute_validStudentIdFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        StudentId targetStudentId = personToDelete.getStudentId();
        DeleteCommand deleteCommand = new DeleteCommand(targetStudentId);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidStudentIdFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person hiddenPerson = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        StudentId hiddenStudentId = hiddenPerson.getStudentId();
        DeleteCommand deleteCommand = new DeleteCommand(hiddenStudentId);

        assertCommandFailure(deleteCommand, model,
                String.format(DeleteCommand.MESSAGE_PERSON_NOT_FOUND, hiddenStudentId));
    }

    @Test
    public void equals() {
        StudentId firstStudentId = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).getStudentId();
        StudentId secondStudentId = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased())
                .getStudentId();

        DeleteCommand deleteFirstCommand = new DeleteCommand(firstStudentId);
        DeleteCommand deleteSecondCommand = new DeleteCommand(secondStudentId);

        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(firstStudentId);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        assertFalse(deleteFirstCommand.equals(1));

        assertFalse(deleteFirstCommand.equals(null));

        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        StudentId targetStudentId = new StudentId("A1234567X");
        DeleteCommand deleteCommand = new DeleteCommand(targetStudentId);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetStudentId=" + targetStudentId + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
