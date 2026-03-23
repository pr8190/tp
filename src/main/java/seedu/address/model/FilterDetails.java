package seedu.address.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores the details of the filter to be applied to the address book.
 */
public class FilterDetails {
    private Set<String> nameKeywords;
    private Set<String> emailKeywords;
    private Set<String> phoneNumberKeywords;
    private Set<String> roomNumberKeywords;
    private Set<String> studentIdKeywords;
    private Set<String> emergencyContactKeywords;
    private Set<String> tagYearKeywords;
    private Set<String> tagMajorKeywords;
    private Set<String> tagGenderKeywords;

    /**
     * Initializes a new {@code FilterDetails} object with empty keyword sets.
     */
    public FilterDetails() {
        this.nameKeywords = new HashSet<>();
        this.emailKeywords = new HashSet<>();
        this.phoneNumberKeywords = new HashSet<>();
        this.roomNumberKeywords = new HashSet<>();
        this.studentIdKeywords = new HashSet<>();
        this.emergencyContactKeywords = new HashSet<>();
        this.tagYearKeywords = new HashSet<>();
        this.tagMajorKeywords = new HashSet<>();
        this.tagGenderKeywords = new HashSet<>();
    }

    /**
     * Creates a copy of the given {@code FilterDetails}.
     *
     * @param other the {@code FilterDetails} to copy
     * @return a new {@code FilterDetails} object with the same details as the given {@code FilterDetails}
     */
    public FilterDetails(FilterDetails other) {
        this.nameKeywords = new HashSet<>(other.nameKeywords);
        this.emailKeywords = new HashSet<>(other.emailKeywords);
        this.phoneNumberKeywords = new HashSet<>(other.phoneNumberKeywords);
        this.roomNumberKeywords = new HashSet<>(other.roomNumberKeywords);
        this.studentIdKeywords = new HashSet<>(other.studentIdKeywords);
        this.emergencyContactKeywords = new HashSet<>(other.emergencyContactKeywords);
        this.tagYearKeywords = new HashSet<>(other.tagYearKeywords);
        this.tagMajorKeywords = new HashSet<>(other.tagMajorKeywords);
        this.tagGenderKeywords = new HashSet<>(other.tagGenderKeywords);
    }

    // ==================== Setters ======================
    public void setNameKeywords(Set<String> nameKeywords) {
        this.nameKeywords = nameKeywords;
    }

    public void setEmailKeywords(Set<String> emailKeywords) {
        this.emailKeywords = emailKeywords;
    }

    public void setPhoneNumberKeywords(Set<String> phoneNumberKeywords) {
        this.phoneNumberKeywords = phoneNumberKeywords;
    }

    public void setRoomNumberKeywords(Set<String> roomNumberKeywords) {
        this.roomNumberKeywords = roomNumberKeywords;
    }

    public void setStudentIdKeywords(Set<String> studentIdKeywords) {
        this.studentIdKeywords = studentIdKeywords;
    }

    public void setEmergencyContactKeywords(Set<String> emergencyContactKeywords) {
        this.emergencyContactKeywords = emergencyContactKeywords;
    }

    public void setTagYearKeywords(Set<String> tagYearKeywords) {
        this.tagYearKeywords = tagYearKeywords;
    }

    public void setTagMajorKeywords(Set<String> tagMajorKeywords) {
        this.tagMajorKeywords = tagMajorKeywords;
    }

    public void setTagGenderKeywords(Set<String> tagGenderKeywords) {
        this.tagGenderKeywords = tagGenderKeywords;
    }

    // ==================== Getters ======================
    public Set<String> getNameKeywords() {
        return nameKeywords;
    }

    public Set<String> getEmailKeywords() {
        return emailKeywords;
    }

    public Set<String> getPhoneNumberKeywords() {
        return phoneNumberKeywords;
    }

    public Set<String> getRoomNumberKeywords() {
        return roomNumberKeywords;
    }

    public Set<String> getStudentIdKeywords() {
        return studentIdKeywords;
    }

    public Set<String> getEmergencyContactKeywords() {
        return emergencyContactKeywords;
    }

    public Set<String> getTagYearKeywords() {
        return tagYearKeywords;
    }

    public Set<String> getTagMajorKeywords() {
        return tagMajorKeywords;
    }

    public Set<String> getTagGenderKeywords() {
        return tagGenderKeywords;
    }

    @Override
    public String toString() {
        return "{"
                + "nameKeywords=" + nameKeywords
                + ", emailKeywords=" + emailKeywords
                + ", phoneNumberKeywords=" + phoneNumberKeywords
                + ", roomNumberKeywords=" + roomNumberKeywords
                + ", studentIdKeywords=" + studentIdKeywords
                + ", emergencyContactKeywords=" + emergencyContactKeywords
                + ", tagYearKeywords=" + tagYearKeywords
                + ", tagMajorKeywords=" + tagMajorKeywords
                + ", tagGenderKeywords=" + tagGenderKeywords
                + '}';
    }
}
