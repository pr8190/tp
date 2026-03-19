package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoomNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RoomNumber(null));
    }

    @Test
    public void constructor_invalidRoomNumber_throwsIllegalArgumentException() {
        String invalidRoomNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new RoomNumber(invalidRoomNumber));
    }

    @Test
    public void isValidRoomNumber() {
        // null RoomNumber
        assertThrows(NullPointerException.class, () -> RoomNumber.isValidRoomNumber(null));

        // blank RoomNumber
        assertFalse(RoomNumber.isValidRoomNumber("")); // empty string
        assertFalse(RoomNumber.isValidRoomNumber(" ")); // spaces only

        // missing parts
        assertFalse(RoomNumber.isValidRoomNumber("1")); // missing letter part
        assertFalse(RoomNumber.isValidRoomNumber("A")); // missing number part

        // invalid parts
        assertFalse(RoomNumber.isValidRoomNumber("A1")); // inverted format
        assertFalse(RoomNumber.isValidRoomNumber("112A")); // 3-digit number
        assertFalse(RoomNumber.isValidRoomNumber("1AA")); // 1+ letters
        assertFalse(RoomNumber.isValidRoomNumber("1@")); //Illegal characters

        // valid RoomNumber
        assertTrue(RoomNumber.isValidRoomNumber("1A"));
        assertTrue(RoomNumber.isValidRoomNumber("99Z")); // period in local part
    }

    @Test
    public void equals() {
        RoomNumber roomNumber = new RoomNumber("10B");

        // same values -> returns true
        assertTrue(roomNumber.equals(new RoomNumber("10B")));

        // same object -> returns true
        assertTrue(roomNumber.equals(roomNumber));

        // null -> returns false
        assertFalse(roomNumber.equals(null));

        // different types -> returns false
        assertFalse(roomNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(roomNumber.equals(new RoomNumber("1A")));
    }
}
