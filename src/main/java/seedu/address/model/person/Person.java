package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.demerit.DemeritIncident;
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
    private final Remark remark;
    private final Map<TagType, Tag> tags;
    private final List<DemeritIncident> demeritIncidents;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, StudentId studentId, RoomNumber roomNumber,
                  EmergencyContact emergencyContact, Remark remark, Map<TagType, Tag> tags) {
        this(name, phone, email, studentId, roomNumber, emergencyContact, remark, tags, List.of());
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, StudentId studentId, RoomNumber roomNumber,
                  EmergencyContact emergencyContact, Remark remark, Map<TagType, Tag> tags,
                  List<DemeritIncident> demeritIncidents) {
        requireAllNonNull(name, phone, email, studentId, roomNumber,
                emergencyContact, remark, tags, demeritIncidents);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentId = studentId;
        this.roomNumber = roomNumber;
        this.emergencyContact = emergencyContact;
        this.remark = remark;
        this.tags = new HashMap<>(tags);
        this.demeritIncidents = new ArrayList<>(demeritIncidents);
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

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag map, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Map<TagType, Tag> getTags() {
        return Collections.unmodifiableMap(tags);
    }

    /**
     * Returns the gender tag of this person, if it exists.
     */
    public Optional<Tag> getGender() {
        return Optional.ofNullable(tags.get(TagType.GENDER));
    }

    /**
     * Returns the year tag of this person, if it exists.
     */
    public Optional<Tag> getYear() {
        return Optional.ofNullable(tags.get(TagType.YEAR));
    }

    /**
     * Returns the major tag of this person, if it exists.
     */
    public Optional<Tag> getMajor() {
        return Optional.ofNullable(tags.get(TagType.MAJOR));
    }

    /**
     * Returns an immutable list of demerit incidents.
     */
    public List<DemeritIncident> getDemeritIncidents() {
        return Collections.unmodifiableList(demeritIncidents);
    }

    /**
     * Returns the resident's total accumulated demerit points.
     */
    public int getTotalDemeritPoints() {
        return demeritIncidents.stream()
                .mapToInt(DemeritIncident::getPointsApplied)
                .sum();
    }

    /**
     * Returns the number of times this resident has already committed the given rule.
     */
    public int getOccurrenceCountForRule(int ruleIndex) {
        return (int) demeritIncidents.stream()
                .filter(incident -> incident.getRuleIndex() == ruleIndex)
                .count();
    }

    /**
     * Returns true if both persons have the same studentId.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getStudentId().equals(getStudentId());
    }

    /**
     * Returns true if both persons have the same room.
     */
    public boolean hasSameRoom(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }
        return otherPerson != null
                && otherPerson.getRoomNumber().equals(getRoomNumber());
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
                && remark.equals(otherPerson.remark)
                && tags.equals(otherPerson.tags)
                && demeritIncidents.equals(otherPerson.demeritIncidents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, studentId, roomNumber,
                emergencyContact, remark, tags, demeritIncidents);
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
                .add("remark", remark)
                .add("tags", tags)
                .add("demeritIncidents", demeritIncidents)
                .toString();
    }
}
