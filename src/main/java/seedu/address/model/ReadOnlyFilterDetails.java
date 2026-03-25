package seedu.address.model;

import javafx.collections.ObservableSet;

/**
 * Unmodifiable view of filter details.
 */
public interface ReadOnlyFilterDetails {
    ObservableSet<String> getNameKeywords();
    ObservableSet<String> getEmailKeywords();
    ObservableSet<String> getPhoneNumberKeywords();
    ObservableSet<String> getRoomNumberKeywords();
    ObservableSet<String> getStudentIdKeywords();
    ObservableSet<String> getEmergencyContactKeywords();
    ObservableSet<String> getTagYearKeywords();
    ObservableSet<String> getTagMajorKeywords();
    ObservableSet<String> getTagGenderKeywords();
}
