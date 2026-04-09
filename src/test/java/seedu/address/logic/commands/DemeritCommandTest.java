package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.format;
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
        Person updatedPerson = findPersonByStudentId(model, ALICE.getStudentId());

        assertEquals(6, updatedPerson.getTotalDemeritPoints());
        assertEquals(1, updatedPerson.getDemeritIncidents().size());
        assertEquals("Stayed over", updatedPerson.getDemeritIncidents().get(0).getRemark());
        assertEquals(String.format(
                DemeritCommand.MESSAGE_ADD_DEMERIT_SUCCESS,
                format(updatedPerson),
                18,
                "Visit by non-residents of the hostel or visiting a resident of another hostel during quiet hours",
                "Stayed over",
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

        Person updatedPerson = findPersonByStudentId(model, aliceId);

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

    @Test
    public void execute_invalidRuleIndex_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DemeritCommand command = new DemeritCommand(ALICE.getStudentId(), 999, "");

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_emptyRemark_incidentAddedWithEmptyRemark() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DemeritCommand command = new DemeritCommand(ALICE.getStudentId(), 18, "");

        command.execute(model);

        Person updatedPerson = findPersonByStudentId(model, ALICE.getStudentId());
        assertEquals(1, updatedPerson.getDemeritIncidents().size());
        assertEquals("", updatedPerson.getDemeritIncidents().get(0).getRemark());
    }

    @Test
    public void execute_differentRules_trackOffencesIndependently() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StudentId aliceId = ALICE.getStudentId();

        new DemeritCommand(aliceId, 18, "").execute(model);
        new DemeritCommand(aliceId, 21, "").execute(model);

        Person updatedPerson = findPersonByStudentId(model, aliceId);

        assertEquals(2, updatedPerson.getDemeritIncidents().size());
        assertEquals(18, updatedPerson.getDemeritIncidents().get(0).getRuleIndex());
        assertEquals(21, updatedPerson.getDemeritIncidents().get(1).getRuleIndex());
        assertEquals(1, updatedPerson.getDemeritIncidents().get(0).getOffenceNumber());
        assertEquals(1, updatedPerson.getDemeritIncidents().get(1).getOffenceNumber());
    }

    private Person findPersonByStudentId(Model model, StudentId studentId) {
        return model.getFilteredPersonList().stream()
                .filter(person -> person.getStudentId().equals(studentId))
                .findFirst()
                .orElseThrow();
    }
}
