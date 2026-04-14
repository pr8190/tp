package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.FilterDetails;

public class PersonMatchesDetailsPredicateTest {

    @Test
    public void test_emptyFilter_matchesAnyPerson() {
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(new FilterDetails());

        assertTrue(predicate.test(AMY));
        assertTrue(predicate.test(BOB));
    }

    @Test
    public void test_nameKeyword_matchesOnlyExpectedPerson() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(Set.of("amy"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);

        assertTrue(predicate.test(AMY));
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_fieldKeywords_matchExpectedFields() {
        FilterDetails emailDetails = new FilterDetails();
        emailDetails.setEmailKeywords(Set.of(VALID_EMAIL_BOB));
        PersonMatchesDetailsPredicate emailPredicate = new PersonMatchesDetailsPredicate(emailDetails);
        assertTrue(emailPredicate.test(BOB));
        assertFalse(emailPredicate.test(AMY));

        FilterDetails phoneDetails = new FilterDetails();
        phoneDetails.setPhoneNumberKeywords(Set.of(VALID_PHONE_AMY));
        PersonMatchesDetailsPredicate phonePredicate = new PersonMatchesDetailsPredicate(phoneDetails);
        assertTrue(phonePredicate.test(AMY));
        assertFalse(phonePredicate.test(BOB));

        FilterDetails roomDetails = new FilterDetails();
        roomDetails.setRoomNumberKeywords(Set.of(VALID_ROOM_NUMBER_BOB));
        PersonMatchesDetailsPredicate roomPredicate = new PersonMatchesDetailsPredicate(roomDetails);
        assertTrue(roomPredicate.test(BOB));
        assertFalse(roomPredicate.test(AMY));

        FilterDetails studentIdDetails = new FilterDetails();
        studentIdDetails.setStudentIdKeywords(Set.of(VALID_STUDENTID_AMY));
        PersonMatchesDetailsPredicate studentIdPredicate = new PersonMatchesDetailsPredicate(studentIdDetails);
        assertTrue(studentIdPredicate.test(AMY));
        assertFalse(studentIdPredicate.test(BOB));
    }

    @Test
    public void test_tagMajorKeyword_fuzzyMatchTrue() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setTagMajorKeywords(Set.of("CS Mazhs"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);

        assertTrue(predicate.test(BENSON));
        assertFalse(predicate.test(AMY));
    }

    @Test
    public void test_emergencyContactKeyword_fuzzyMatchTrue() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setEmergencyContactKeywords(Set.of("+65 98765433"));
        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);

        assertTrue(predicate.test(BOB));
        assertFalse(predicate.test(AMY));
    }

    @Test
    public void test_studentIdKeyword_requiresExactMatchIgnoringCase() {
        // Exact match (case-insensitive) should succeed
        FilterDetails exactIdDetails = new FilterDetails();
        exactIdDetails.setStudentIdKeywords(Set.of(VALID_STUDENTID_AMY));
        PersonMatchesDetailsPredicate exactIdPredicate = new PersonMatchesDetailsPredicate(exactIdDetails);
        assertTrue(exactIdPredicate.test(AMY));

        // Partial/substring match should fail
        FilterDetails partialIdDetails = new FilterDetails();
        partialIdDetails.setStudentIdKeywords(Set.of("1234"));
        PersonMatchesDetailsPredicate partialIdPredicate = new PersonMatchesDetailsPredicate(partialIdDetails);
        assertFalse(partialIdPredicate.test(AMY));
    }

    @Test
    public void test_tagGenderKeyword_requiresExactMatchIgnoringCase() {
        FilterDetails exactGenderDetails = new FilterDetails();
        exactGenderDetails.setTagGenderKeywords(Set.of("she/her"));
        PersonMatchesDetailsPredicate exactGenderPredicate = new PersonMatchesDetailsPredicate(exactGenderDetails);
        assertTrue(exactGenderPredicate.test(ALICE));

        FilterDetails partialGenderDetails = new FilterDetails();
        partialGenderDetails.setTagGenderKeywords(Set.of("f"));
        PersonMatchesDetailsPredicate partialGenderPredicate = new PersonMatchesDetailsPredicate(partialGenderDetails);
        assertFalse(partialGenderPredicate.test(ALICE));
    }

    @Test
    public void test_multipleCriteria_allMustMatch() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(Set.of("amy"));
        filterDetails.setStudentIdKeywords(Set.of(VALID_STUDENTID_AMY));

        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);

        assertTrue(predicate.test(AMY));
        assertFalse(predicate.test(BOB));
    }

    @Test
    public void test_roomKeyword_noMatch() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setRoomNumberKeywords(Set.of(VALID_ROOM_NUMBER_BOB));

        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        assertFalse(predicate.test(AMY));
    }

    @Test
    public void test_studentIdKeyword_noMatch() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setStudentIdKeywords(Set.of(VALID_STUDENTID_BOB));

        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        assertFalse(predicate.test(AMY));
    }

    @Test
    public void test_tagYearKeyword_noMatch() {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setTagYearKeywords(Set.of("Y9"));

        PersonMatchesDetailsPredicate predicate = new PersonMatchesDetailsPredicate(filterDetails);
        assertFalse(predicate.test(AMY));
    }

    @Test
    public void equals() {
        FilterDetails firstDetails = new FilterDetails();
        firstDetails.setNameKeywords(Set.of("amy"));
        PersonMatchesDetailsPredicate firstPredicate = new PersonMatchesDetailsPredicate(firstDetails);

        FilterDetails secondDetails = new FilterDetails();
        secondDetails.setNameKeywords(Set.of("amy"));
        PersonMatchesDetailsPredicate secondPredicate = new PersonMatchesDetailsPredicate(secondDetails);

        assertEquals(firstPredicate, firstPredicate);
        assertEquals(firstPredicate, secondPredicate);
        assertNotEquals(null, firstPredicate);
        assertNotEquals(1, firstPredicate);
    }
}
