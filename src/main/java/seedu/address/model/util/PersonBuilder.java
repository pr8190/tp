package seedu.address.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.demerit.DemeritIncident;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "+65 85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_STUDENTID = "A0123456X";
    public static final String DEFAULT_ROOM_NUMBER = "1A";
    public static final String DEFAULT_EMERGENCY_CONTACT = "+65 91234567";
    public static final String DEFAULT_REMARK = "";

    private Name name;
    private Phone phone;
    private Email email;
    private StudentId studentId;
    private RoomNumber roomNumber;
    private EmergencyContact emergencyContact;
    private Map<TagType, Tag> tags = new HashMap<>();
    private Remark remark;
    private List<DemeritIncident> demeritIncidents = new ArrayList<>();

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        studentId = new StudentId(DEFAULT_STUDENTID);
        roomNumber = new RoomNumber(DEFAULT_ROOM_NUMBER);
        emergencyContact = new EmergencyContact(DEFAULT_EMERGENCY_CONTACT);
        remark = new Remark(DEFAULT_REMARK);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        studentId = personToCopy.getStudentId();
        roomNumber = personToCopy.getRoomNumber();
        emergencyContact = personToCopy.getEmergencyContact();
        remark = personToCopy.getRemark();
        tags = new HashMap<>(personToCopy.getTags());
        demeritIncidents = new ArrayList<>(personToCopy.getDemeritIncidents());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(Object[]... tags) {
        this.tags = TagUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code tags} of the {@code Person} that we are building.
     */
    public PersonBuilder withTags(Map<TagType, Tag> tags) {
        this.tags = new HashMap<>(tags);
        return this;
    }

    /**
     * Sets the {@code StudentId} of the {@code Person} that we are building.
     */
    public PersonBuilder withStudentId(String studentId) {
        this.studentId = new StudentId(studentId);
        return this;
    }

    /**
     * Sets the {@code RoomNumber} of the {@code Person} that we are building.
     */
    public PersonBuilder withRoomNumber(String roomNumber) {
        this.roomNumber = new RoomNumber(roomNumber);
        return this;
    }

    /**
     * Sets the {@code EmergencyContact} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmergencyContact(String emergencyContact) {
        this.emergencyContact = new EmergencyContact(emergencyContact);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    /**
     * Parses the demerit incidents from the given tuples: (ruleIndex, offenceNumber, remark).
     */
    public PersonBuilder withDemeritIncidents(Object[]... incidents) {
        this.demeritIncidents = DemeritIncidentUtil.getDemeritIncidentList(incidents);
        return this;
    }

    /**
     * Builds and returns the {@code Person} object.
     *
     * @return the built Person object
     */
    public Person build() {
        return new Person(name, phone, email, studentId, roomNumber, emergencyContact, remark, tags,
                demeritIncidents);
    }
}
