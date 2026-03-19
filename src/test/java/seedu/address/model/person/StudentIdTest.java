package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StudentIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StudentId(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new StudentId(invalidAddress));
    }

    @Test
    public void isValidStudentId() {
        // null studentId
        assertThrows(NullPointerException.class, () -> StudentId.isValidStudentId(null));

        // invalid studentIds
        assertFalse(StudentId.isValidStudentId("")); // empty string
        assertFalse(StudentId.isValidStudentId(" ")); // spaces only
        assertFalse(StudentId.isValidStudentId("A1111111")); //Does not end with a letter
        assertFalse(StudentId.isValidStudentId("2222222X")); //Does not start with A
        assertFalse(StudentId.isValidStudentId("B2222222X")); //Does not start with A

        // valid studentIds
        assertTrue(StudentId.isValidStudentId("A4487884X"));
        assertTrue(StudentId.isValidStudentId("A1234567Y"));
    }

    @Test
    public void equals() {
        StudentId studentId = new StudentId("A1234567X");

        // same values -> returns true
        assertTrue(studentId.equals(new StudentId("A1234567X")));

        // same object -> returns true
        assertTrue(studentId.equals(studentId));

        // null -> returns false
        assertFalse(studentId.equals(null));

        // different types -> returns false
        assertFalse(studentId.equals(5.0f));

        // different values -> returns false
        assertFalse(studentId.equals(new StudentId("A7654321Z")));
    }
}
