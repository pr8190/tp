package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.FilterDetails;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PersonMatchesDetailsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));

        // Test PersonMatchesDetailsPredicate
        FilterDetails filterDetails1 = createEmptyFilterDetails();
        filterDetails1.setNameKeywords(Set.of("first"));
        PersonMatchesDetailsPredicate firstDetailsPredicate = new PersonMatchesDetailsPredicate(filterDetails1);

        FilterDetails filterDetails2 = createEmptyFilterDetails();
        filterDetails2.setNameKeywords(Set.of("second"));
        PersonMatchesDetailsPredicate secondDetailsPredicate = new PersonMatchesDetailsPredicate(filterDetails2);

        FindCommand findFirstDetailsCommand = new FindCommand(firstDetailsPredicate);
        FindCommand findSecondDetailsCommand = new FindCommand(secondDetailsPredicate);

        // same values -> returns true
        FindCommand findFirstDetailsCommandCopy = new FindCommand(firstDetailsPredicate);
        assertTrue(findFirstDetailsCommand.equals(findFirstDetailsCommandCopy));

        // different values -> returns false
        assertFalse(findFirstDetailsCommand.equals(findSecondDetailsCommand));

        // different predicate type -> returns false
        assertFalse(findFirstCommand.equals(findFirstDetailsCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
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
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void execute_filterByTag_MultiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        FilterDetails filterDetails = createEmptyFilterDetails();
        // BENSON, Carl, Elle, Fiona, and George have matches for Major CS.
        filterDetails.setTagMajorKeywords(Set.of("CS"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, CARL, ELLE, FIONA, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_filterByMultipleFields_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        FilterDetails filterDetails = createEmptyFilterDetails();
        // ALICE: Name "Alice", Phone "94351253"
        filterDetails.setNameKeywords(Set.of("Alice"));
        filterDetails.setPhoneNumberKeywords(Set.of("94351253"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindCommand findCommand = new FindCommand(predicate);
        String expected = FindCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());

        FilterDetails filterDetails = createEmptyFilterDetails();
        PersonMatchesDetailsPredicate detailsPredicate = new PersonMatchesDetailsPredicate(filterDetails);
        FindCommand findDetailsCommand = new FindCommand(detailsPredicate);
        String expectedDetails = FindCommand.class.getCanonicalName() + "{predicate=" + detailsPredicate + "}";
        assertEquals(expectedDetails, findDetailsCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
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
