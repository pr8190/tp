package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String DUPLICATE_TAG_TYPE_MESSAGE_FORMAT =
            "Duplicate tag type found in person data: %s";

    private final String name;
    private final String phone;
    private final String email;
    private final String studentId;
    private final String roomNumber;
    private final String emergencyContact;
    private final String remark;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedDemeritIncident> demeritIncidents = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
                             @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email,
                             @JsonProperty("studentId") String studentId,
                             @JsonProperty("roomNumber") String roomNumber,
                             @JsonProperty("emergencyContact") String emergencyContact,
                             @JsonProperty("remark") String remark,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("demeritIncidents")
                             List<JsonAdaptedDemeritIncident> demeritIncidents) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.studentId = studentId;
        this.roomNumber = roomNumber;
        this.emergencyContact = emergencyContact;
        this.remark = remark;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (demeritIncidents != null) {
            this.demeritIncidents.addAll(demeritIncidents);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        studentId = source.getStudentId().value;
        roomNumber = source.getRoomNumber().value;
        emergencyContact = source.getEmergencyContact().value;
        remark = source.getRemark().toString();
        tags.addAll(source.getTags().values().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        demeritIncidents.addAll(source.getDemeritIncidents().stream()
                .map(JsonAdaptedDemeritIncident::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (studentId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentId.class.getSimpleName()));
        }
        if (!StudentId.isValidStudentId(studentId)) {
            throw new IllegalValueException(StudentId.MESSAGE_CONSTRAINTS);
        }
        final StudentId modelStudentId = new StudentId(studentId);

        if (roomNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RoomNumber.class.getSimpleName()));
        }
        if (!RoomNumber.isValidRoomNumber(roomNumber)) {
            throw new IllegalValueException(RoomNumber.MESSAGE_CONSTRAINTS);
        }
        final RoomNumber modelRoomNumber = new RoomNumber(roomNumber);

        if (emergencyContact == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EmergencyContact.class.getSimpleName()));
        }
        if (!EmergencyContact.isValidEmergencyContact(emergencyContact)) {
            throw new IllegalValueException(EmergencyContact.MESSAGE_CONSTRAINTS);
        }
        final EmergencyContact modelEmergencyContact = new EmergencyContact(emergencyContact);

        final Remark modelRemark = new Remark(remark == null ? "" : remark);

        final HashMap<TagType, Tag> modelTags = new HashMap<>();
        for (JsonAdaptedTag jsonAdaptedTag : tags) {
            Tag tag = jsonAdaptedTag.toModelType();
            TagType tagType = tag.getTagType();
            if (modelTags.containsKey(tagType)) {
                throw new IllegalValueException(String.format(DUPLICATE_TAG_TYPE_MESSAGE_FORMAT, tagType));
            }
            modelTags.put(tagType, tag);
        }

        final List<DemeritIncident> modelDemeritIncidents = new ArrayList<>();
        for (JsonAdaptedDemeritIncident jsonAdaptedDemeritIncident : demeritIncidents) {
            modelDemeritIncidents.add(jsonAdaptedDemeritIncident.toModelType());
        }

        return new Person(modelName, modelPhone, modelEmail, modelStudentId, modelRoomNumber,
                modelEmergencyContact, modelRemark, modelTags, modelDemeritIncidents);
    }

    /**
     * Jackson-friendly version of {@link DemeritIncident}.
     */
    private static class JsonAdaptedDemeritIncident {
        private final int ruleIndex;
        private final String ruleTitle;
        private final int offenceNumber;
        private final int pointsApplied;
        private final String remark;

        @JsonCreator
        public JsonAdaptedDemeritIncident(@JsonProperty("ruleIndex") int ruleIndex,
                                          @JsonProperty("ruleTitle") String ruleTitle,
                                          @JsonProperty("offenceNumber") int offenceNumber,
                                          @JsonProperty("pointsApplied") int pointsApplied,
                                          @JsonProperty("remark") String remark) {
            this.ruleIndex = ruleIndex;
            this.ruleTitle = ruleTitle;
            this.offenceNumber = offenceNumber;
            this.pointsApplied = pointsApplied;
            this.remark = remark;
        }

        JsonAdaptedDemeritIncident(DemeritIncident source) {
            this.ruleIndex = source.getRuleIndex();
            this.ruleTitle = source.getRuleTitle();
            this.offenceNumber = source.getOffenceNumber();
            this.pointsApplied = source.getPointsApplied();
            this.remark = source.getRemark();
        }

        DemeritIncident toModelType() {
            return new DemeritIncident(ruleIndex, ruleTitle, offenceNumber, pointsApplied,
                    remark == null ? "" : remark);
        }
    }
}
