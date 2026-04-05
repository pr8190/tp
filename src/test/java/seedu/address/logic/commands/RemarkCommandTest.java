package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.RemarkCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.model.person.StudentId;
import seedu.address.model.util.PersonBuilder;

public class RemarkCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validRemark_success() throws CommandException {
        Person newPerson = new PersonBuilder().withStudentId(VALID_STUDENTID_AMY).build();
        model.addPerson(newPerson);

        RemarkCommand remarkCommand = new RemarkCommand(new StudentId(VALID_STUDENTID_AMY), new Remark(VALID_REMARK));
        CommandResult remarkCommandResult = remarkCommand.execute(model);

        Person remarkedPerson = model.getPersonByStudentId(new StudentId(VALID_STUDENTID_AMY)).get();
        CommandResult expectedCommand = new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(remarkedPerson)));

        assertEquals(remarkedPerson.getRemark(), new Remark(VALID_REMARK));

        assertEquals(remarkCommandResult, expectedCommand);
    }

    @Test
    public void execute_invalidStudentId_throwsCommandException() {
        StudentId invalidStudentId = new StudentId("A9999999N");
        RemarkCommand remarkCommand = new RemarkCommand(invalidStudentId, new Remark(VALID_REMARK));
        assertThrows(CommandException.class, () -> remarkCommand.execute(model));
    }

    @Test
    public void execute_emptyRemark_clearsRemark() throws CommandException {
        Person newPerson = new PersonBuilder().withStudentId(VALID_STUDENTID_AMY).withRemark(VALID_REMARK).build();
        model.addPerson(newPerson);

        RemarkCommand remarkCommand = new RemarkCommand(new StudentId(VALID_STUDENTID_AMY), new Remark(""));
        remarkCommand.execute(model);

        Person remarkedPerson = model.getPersonByStudentId(new StudentId(VALID_STUDENTID_AMY)).get();
        assertEquals(remarkedPerson.getRemark(), new Remark(""));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        RemarkCommand addAliceRemark = new RemarkCommand(alice.getStudentId(), new Remark(VALID_REMARK));

        // same object -> returns true
        assertEquals(addAliceRemark, addAliceRemark);

        // same values -> returns true
        RemarkCommand addAliceRemarkCopy = new RemarkCommand(alice.getStudentId(), new Remark(VALID_REMARK));
        assertEquals(addAliceRemark, addAliceRemarkCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceRemark);

        // null -> returns false
        assertNotEquals(null, addAliceRemark);
    }
}
