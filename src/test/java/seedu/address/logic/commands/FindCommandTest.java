package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.FilterDetails;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonMatchesDetailsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        FilterDetails firstFilterDetails = createEmptyFilterDetails();
        firstFilterDetails.setNameKeywords(Set.of("first"));
        FilterDetails secondFilterDetails = createEmptyFilterDetails();
        secondFilterDetails.setNameKeywords(Set.of("second"));

        FindCommand findFirstCommand = new FindCommand(firstFilterDetails);
        FindCommand findSecondCommand = new FindCommand(secondFilterDetails);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstFilterDetails);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FilterDetails filterDetails = createEmptyFilterDetails();
        filterDetails.setNameKeywords(Set.of("Kurz", "Elle", "Kunz"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand command = new FindCommand(filterDetails);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    @Test
    public void execute_filterByPhone_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterDetails filterDetails = createEmptyFilterDetails();
        filterDetails.setPhoneNumberKeywords(Set.of("94351253")); // ALICE's phone
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand command = new FindCommand(filterDetails);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_filterByTag_multiplePersonsFound() {
        // Alice (MAJOR=CS) and Benson (MAJOR=Maths) are both Year 1 and Year 2 respectively.
        // Using GENDER=Female to get multiple matches: Alice, Elle, Fiona
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FilterDetails filterDetails = createEmptyFilterDetails();
        filterDetails.setTagGenderKeywords(Set.of("Female"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand command = new FindCommand(filterDetails);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, FIONA), model.getFilteredPersonList());
    }
    @Test
    public void execute_filterByTag_noPersonsFound() {
        // Zero persons: No one studies CS, is Male, and is in Year 4.
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterDetails filterDetails = createEmptyFilterDetails();
        filterDetails.setTagMajorKeywords(Set.of("CS"));
        filterDetails.setTagGenderKeywords(Set.of("Male"));
        filterDetails.setTagYearKeywords(Set.of("4"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand command = new FindCommand(filterDetails);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(List.of(), model.getFilteredPersonList());
    }


    @Test
    public void execute_filterByMultipleFields_personFound() {
        // ALICE: Name "Alice", Phone "94351253"
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterDetails filterDetails = createEmptyFilterDetails();
        filterDetails.setNameKeywords(Set.of("Alice"));
        filterDetails.setPhoneNumberKeywords(Set.of("94351253"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand command = new FindCommand(filterDetails);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.singletonList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        FilterDetails filterDetails = createEmptyFilterDetails();
        FindCommand findDetailsCommand = new FindCommand(filterDetails);
        PersonMatchesDetailsPredicate detailsPredicate = new PersonMatchesDetailsPredicate(filterDetails);
        String expectedDetails = FindCommand.class.getCanonicalName() + "{predicate=" + detailsPredicate + "}";
        assertEquals(expectedDetails, findDetailsCommand.toString());
    }


    private FilterDetails createEmptyFilterDetails() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(Collections.emptySet());
        filterDetails.setEmailKeywords(Collections.emptySet());
        filterDetails.setPhoneNumberKeywords(Collections.emptySet());
        filterDetails.setRoomNumberKeywords(Collections.emptySet());
        filterDetails.setStudentIdKeywords(Collections.emptySet());
        filterDetails.setEmergencyContactKeywords(Collections.emptySet());
        filterDetails.setTagYearKeywords(Collections.emptySet());
        filterDetails.setTagMajorKeywords(Collections.emptySet());
        filterDetails.setTagGenderKeywords(Collections.emptySet());
        return filterDetails;
    }
}
