package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's emergency contact in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmergencyContact(String)}
 */
public class EmergencyContact {
    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should be in the format of +<country code> <number>, with the <country code> being between "
                    + "1-3 digits and the <number> between 3-15 digits. It should only contain numbers and" + " '+'s";
    public static final String VALIDATION_REGEX = "^\\+\\d{1,3}([ -]?(\\(\\d{3,15}\\)|\\d{3,15}))+$";
    public final String value;

    /**
     * Constructs a {@code EmergencyContact}.
     *
     * @param emergencyPhone A valid emergency phone number.
     */
    public EmergencyContact(String emergencyPhone) {
        requireNonNull(emergencyPhone);
        checkArgument(isValidEmergencyContact(emergencyPhone), MESSAGE_CONSTRAINTS);
        value = emergencyPhone;
    }

    /**
     * Returns true if a given string is a valid emergency contact number.
     */
    public static boolean isValidEmergencyContact(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmergencyContact)) {
            return false;
        }

        EmergencyContact otherEmergencyContact = (EmergencyContact) other;
        return value.equals(otherEmergencyContact.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
