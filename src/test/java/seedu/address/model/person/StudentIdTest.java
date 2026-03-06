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

        // valid studentIds
        assertTrue(StudentId.isValidStudentId("Blk 456, Den Road, #01-355"));
        assertTrue(StudentId.isValidStudentId("-")); // one character
        assertTrue(StudentId.isValidStudentId("A1234567"));
    }

    @Test
    public void equals() {
        StudentId studentId = new StudentId("Valid StudentId");

        // same values -> returns true
        assertTrue(studentId.equals(new StudentId("Valid StudentId")));

        // same object -> returns true
        assertTrue(studentId.equals(studentId));

        // null -> returns false
        assertFalse(studentId.equals(null));

        // different types -> returns false
        assertFalse(studentId.equals(5.0f));

        // different values -> returns false
        assertFalse(studentId.equals(new StudentId("Other Valid StudentId")));
    }
}
