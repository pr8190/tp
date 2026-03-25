package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

/**
 * Stores the details of the filter to be applied to the address book.
 */
public class FilterDetails implements ReadOnlyFilterDetails {
    private final ObservableSet<String> nameKeywords;
    private final ObservableSet<String> emailKeywords;
    private final ObservableSet<String> phoneNumberKeywords;
    private final ObservableSet<String> roomNumberKeywords;
    private final ObservableSet<String> studentIdKeywords;
    private final ObservableSet<String> emergencyContactKeywords;
    private final ObservableSet<String> tagYearKeywords;
    private final ObservableSet<String> tagMajorKeywords;
    private final ObservableSet<String> tagGenderKeywords;

    /**
     * Initializes a new {@code FilterDetails} object with empty keyword sets.
     */
    public FilterDetails() {
        this.nameKeywords = FXCollections.observableSet();
        this.emailKeywords = FXCollections.observableSet();
        this.phoneNumberKeywords = FXCollections.observableSet();
        this.roomNumberKeywords = FXCollections.observableSet();
        this.studentIdKeywords = FXCollections.observableSet();
        this.emergencyContactKeywords = FXCollections.observableSet();
        this.tagYearKeywords = FXCollections.observableSet();
        this.tagMajorKeywords = FXCollections.observableSet();
        this.tagGenderKeywords = FXCollections.observableSet();
    }

    /**
     * Initializes a new {@code FilterDetails} object as a copy of the given read-only details.
     */
    public FilterDetails(ReadOnlyFilterDetails other) {
        this();
        set(other);
    }

    // ==================== Setters ======================

    /**
     * Sets the details of this {@code FilterDetails} to be the same as the given {@code ReadOnlyFilterDetails}.
     *
     * @param other the {@code ReadOnlyFilterDetails} to copy the details from
     */
    public void set(ReadOnlyFilterDetails other) {
        requireNonNull(other);
        replaceAll(nameKeywords, other.getNameKeywords());
        replaceAll(emailKeywords, other.getEmailKeywords());
        replaceAll(phoneNumberKeywords, other.getPhoneNumberKeywords());
        replaceAll(roomNumberKeywords, other.getRoomNumberKeywords());
        replaceAll(studentIdKeywords, other.getStudentIdKeywords());
        replaceAll(emergencyContactKeywords, other.getEmergencyContactKeywords());
        replaceAll(tagYearKeywords, other.getTagYearKeywords());
        replaceAll(tagMajorKeywords, other.getTagMajorKeywords());
        replaceAll(tagGenderKeywords, other.getTagGenderKeywords());
    }

    // ==================== Getters ======================
    public ObservableSet<String> getNameKeywords() {
        return nameKeywords;
    }

    public ObservableSet<String> getEmailKeywords() {
        return emailKeywords;
    }

    public ObservableSet<String> getPhoneNumberKeywords() {
        return phoneNumberKeywords;
    }

    public ObservableSet<String> getRoomNumberKeywords() {
        return roomNumberKeywords;
    }

    public ObservableSet<String> getStudentIdKeywords() {
        return studentIdKeywords;
    }

    public ObservableSet<String> getEmergencyContactKeywords() {
        return emergencyContactKeywords;
    }

    public ObservableSet<String> getTagYearKeywords() {
        return tagYearKeywords;
    }

    public ObservableSet<String> getTagMajorKeywords() {
        return tagMajorKeywords;
    }

    public ObservableSet<String> getTagGenderKeywords() {
        return tagGenderKeywords;
    }

    // ==================== Setters ======================

    public void setNameKeywords(Set<String> nameKeywords) {
        replaceAll(this.nameKeywords, nameKeywords);
    }

    public void setEmailKeywords(Set<String> emailKeywords) {
        replaceAll(this.emailKeywords, emailKeywords);
    }

    public void setPhoneNumberKeywords(Set<String> phoneNumberKeywords) {
        replaceAll(this.phoneNumberKeywords, phoneNumberKeywords);
    }

    public void setRoomNumberKeywords(Set<String> roomNumberKeywords) {
        replaceAll(this.roomNumberKeywords, roomNumberKeywords);
    }

    public void setStudentIdKeywords(Set<String> studentIdKeywords) {
        replaceAll(this.studentIdKeywords, studentIdKeywords);
    }

    public void setEmergencyContactKeywords(Set<String> emergencyContactKeywords) {
        replaceAll(this.emergencyContactKeywords, emergencyContactKeywords);
    }

    public void setTagYearKeywords(Set<String> tagYearKeywords) {
        replaceAll(this.tagYearKeywords, tagYearKeywords);
    }

    public void setTagMajorKeywords(Set<String> tagMajorKeywords) {
        replaceAll(this.tagMajorKeywords, tagMajorKeywords);
    }

    public void setTagGenderKeywords(Set<String> tagGenderKeywords) {
        replaceAll(this.tagGenderKeywords, tagGenderKeywords);
    }

    private void replaceAll(Set<String> target, Set<String> source) {
        requireNonNull(source);
        target.clear();
        target.addAll(source);
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
