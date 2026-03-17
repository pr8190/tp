package seedu.address.model;

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
    private Set<String> tagKeywords;
    private Set<String> tagYearKeywords;
    private Set<String> tagMajorKeywords;
    private Set<String> tagGenderKeywords;

    public FilterDetails() {
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

    public void setTagKeywords(Set<String> tagKeywords) {
        this.tagKeywords = tagKeywords;
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

    public Set<String> getTagKeywords() {
        return tagKeywords;
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
}
