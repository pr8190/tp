package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmergencyContactTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EmergencyContact(null));
    }

    @Test
    public void constructor_invalidEmergencyContact_throwsIllegalArgumentException() {
        String invalidEmergencyContact = "";
        assertThrows(IllegalArgumentException.class, () -> new EmergencyContact(invalidEmergencyContact));
    }

    @Test
    public void isValidEmergencyContact() {
        // null emergency contact number
        assertThrows(NullPointerException.class, () -> EmergencyContact.isValidEmergencyContact(null));

        // invalid emergency contact numbers
        assertFalse(EmergencyContact.isValidEmergencyContact("")); // empty string
        assertFalse(EmergencyContact.isValidEmergencyContact(" ")); // spaces only
        assertFalse(EmergencyContact.isValidEmergencyContact("91")); // less than 3 numbers
        assertFalse(EmergencyContact.isValidEmergencyContact("emergency contact")); // non-numeric
        assertFalse(EmergencyContact.isValidEmergencyContact("9011p041")); // alphabets within digits
        assertFalse(EmergencyContact.isValidEmergencyContact("9312 1534")); // spaces within digits

        // valid emergency contact numbers
        assertTrue(EmergencyContact.isValidEmergencyContact("+91911")); // exactly 3 numbers
        assertTrue(EmergencyContact.isValidEmergencyContact("+65 93121534"));
        assertTrue(EmergencyContact.isValidEmergencyContact("+51 124293842033123")); // long emergency contact numbers
    }

    @Test
    public void equals() {
        EmergencyContact emergencyContact = new EmergencyContact("+99999");

        // same values -> returns true
        assertTrue(emergencyContact.equals(new EmergencyContact("+99999")));

        // same object -> returns true
        assertTrue(emergencyContact.equals(emergencyContact));

        // null -> returns false
        assertFalse(emergencyContact.equals(null));

        // different types -> returns false
        assertFalse(emergencyContact.equals(5.0f));

        // different values -> returns false
        assertFalse(emergencyContact.equals(new EmergencyContact("+65995")));
    }
}
