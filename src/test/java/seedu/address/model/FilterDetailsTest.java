package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

class FilterDetailsTest {

    @Test
    void equals_allFieldsSame_returnsTrue() {
        FilterDetails first = createFilterDetails("A");
        FilterDetails second = createFilterDetails("A");

        assertEquals(first, second);
    }

    @Test
    void equals_allFieldsDifferent_returnsFalse() {
        FilterDetails first = createFilterDetails("A");
        FilterDetails second = createFilterDetails("B");

        assertNotEquals(first, second);
    }

    @Test
    void toString_allFieldsSet_returnsExpectedFormat() {
        FilterDetails filterDetails = createFilterDetails("A");

        String expected = "{"
                + "nameKeywords=[nameA]"
                + ", emailKeywords=[emailA@example.com]"
                + ", phoneNumberKeywords=[phoneA]"
                + ", roomNumberKeywords=[roomA]"
                + ", studentIdKeywords=[idA]"
                + ", emergencyContactKeywords=[emergencyA]"
                + ", tagYearKeywords=[yearA]"
                + ", tagMajorKeywords=[majorA]"
                + ", tagGenderKeywords=[genderA]"
                + '}';

        assertEquals(expected, filterDetails.toString());
    }

    private FilterDetails createFilterDetails(String suffix) {
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(Set.of("name" + suffix));
        filterDetails.setEmailKeywords(Set.of("email" + suffix + "@example.com"));
        filterDetails.setPhoneNumberKeywords(Set.of("phone" + suffix));
        filterDetails.setRoomNumberKeywords(Set.of("room" + suffix));
        filterDetails.setStudentIdKeywords(Set.of("id" + suffix));
        filterDetails.setEmergencyContactKeywords(Set.of("emergency" + suffix));
        filterDetails.setTagYearKeywords(Set.of("year" + suffix));
        filterDetails.setTagMajorKeywords(Set.of("major" + suffix));
        filterDetails.setTagGenderKeywords(Set.of("gender" + suffix));
        return filterDetails;
    }
}
