package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

public class DemeritCommandTest {

    @Test
    public void execute_firstOffence_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DemeritCommand command = new DemeritCommand(ALICE.getStudentId(), 18, "Stayed over");

        CommandResult result = command.execute(model);

        Person updatedPerson = model.getFilteredPersonList().stream()
                .filter(person -> person.getStudentId().equals(ALICE.getStudentId()))
                .findFirst()
                .orElseThrow();

        assertEquals(6, updatedPerson.getTotalDemeritPoints());
        assertEquals(1, updatedPerson.getDemeritIncidents().size());
        assertEquals(String.format(
                DemeritCommand.MESSAGE_ADD_DEMERIT_SUCCESS,
                seedu.address.logic.Messages.format(updatedPerson),
                18,
                "Visit by non-residents of the hostel or visiting a resident of another hostel during quiet hours",
                6,
                6), result.getFeedbackToUser());
    }

    @Test
    public void execute_repeatedOffence_escalatesPoints() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StudentId aliceId = ALICE.getStudentId();

        new DemeritCommand(aliceId, 18, "").execute(model);
        new DemeritCommand(aliceId, 18, "").execute(model);
        new DemeritCommand(aliceId, 18, "").execute(model);

        Person updatedPerson = model.getFilteredPersonList().stream()
                .filter(person -> person.getStudentId().equals(aliceId))
                .findFirst()
                .orElseThrow();

        assertEquals(24, updatedPerson.getTotalDemeritPoints());
        assertEquals(3, updatedPerson.getDemeritIncidents().size());
        assertEquals(1, updatedPerson.getDemeritIncidents().get(0).getOffenceNumber());
        assertEquals(2, updatedPerson.getDemeritIncidents().get(1).getOffenceNumber());
        assertEquals(3, updatedPerson.getDemeritIncidents().get(2).getOffenceNumber());
        assertEquals(6, updatedPerson.getDemeritIncidents().get(0).getPointsApplied());
        assertEquals(9, updatedPerson.getDemeritIncidents().get(1).getPointsApplied());
        assertEquals(9, updatedPerson.getDemeritIncidents().get(2).getPointsApplied());
    }

    @Test
    public void execute_invalidStudentId_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DemeritCommand command = new DemeritCommand(new StudentId("A9999999Z"), 18, "");

        assertThrows(CommandException.class, () -> command.execute(model));
    }
}
