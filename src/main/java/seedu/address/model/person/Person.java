package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final StudentId studentId;

    // Data fields
    private final RoomNumber roomNumber;
    private final EmergencyContact emergencyContact;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, StudentId studentId, RoomNumber roomNumber,
                  EmergencyContact emergencyContact, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, studentId, roomNumber, emergencyContact, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentId = studentId;
        this.roomNumber = roomNumber;
        this.emergencyContact = emergencyContact;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public StudentId getStudentId() {
        return studentId;
    }

    public RoomNumber getRoomNumber() {
        return roomNumber;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }
    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * @return the gender tag(s) of this person, if they exist
     */
    public Set<Tag> getGender() {
        Set<Tag> genderTags = tags.stream()
                .filter(tag -> tag.getTagType() == TagType.GENDER)
                .collect(Collectors.toSet());
        assertTagLimit(genderTags, TagType.GENDER);
        return genderTags;
    }

    /*
     * @return the year tags of this person
     */
    public Set<Tag> getYear() {
        Set<Tag> yearTags = tags.stream()
                .filter(tag -> tag.getTagType() == TagType.YEAR)
                .collect(Collectors.toSet());
        assertTagLimit(yearTags, TagType.YEAR);
        return yearTags;
    }

    /*
     * @return the year tags of this person, if they exist.
     */
    public Set<Tag> getMajor() {
        Set<Tag> majorTags = tags.stream()
                .filter(tag -> tag.getTagType() == TagType.MAJOR)
                .collect(Collectors.toSet());
        assertTagLimit(majorTags, TagType.MAJOR);
        return majorTags;
    }

    /**
     * Asserts that the number of tags of a given type does not exceed the allowed maximum.
     */
    private static void assertTagLimit(Set<Tag> tagSet, TagType type) {
        assert tagSet.size() <= type.getMaxTagsPerType()
                : "Tag count for " + type + " exceeds limit of " + type.getMaxTagsPerType();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && studentId.equals(otherPerson.studentId)
                && roomNumber.equals(otherPerson.roomNumber)
                && emergencyContact.equals(otherPerson.emergencyContact)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, studentId, roomNumber, emergencyContact, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("studentId", studentId)
                .add("roomNumber", roomNumber)
                .add("emergencyContact", emergencyContact)
                .add("tags", tags)
                .toString();
    }

}
