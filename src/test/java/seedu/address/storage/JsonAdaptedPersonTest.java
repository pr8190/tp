package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.demerit.DemeritIncident;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmergencyContact;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RoomNumber;
import seedu.address.model.person.StudentId;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R%chel";
    private static final String INVALID_PHONE = "651234";
    private static final String INVALID_STUDENTID = "A1234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_ROOM_NUMBER = " ";
    private static final String INVALID_EMERGENCY_CONTACT = "911a";
    private static final String INVALID_TAG = "#friend";
    private static final String VALID_REMARK = "Allergic to peanuts";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_STUDENTID = BENSON.getStudentId().toString();
    private static final String VALID_ROOM_NUMBER = BENSON.getRoomNumber().toString();
    private static final String VALID_EMERGENCY_CONTACT = BENSON.getEmergencyContact().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().values().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_personWithDemerits_roundTripsSuccessfully() throws Exception {
        List<DemeritIncident> incidents = List.of(
                new DemeritIncident(18,
                        "Visit by non-residents of the hostel or visiting a resident "
                                + "of another hostel during quiet hours",
                        1,
                        6,
                        "Visitor stayed during quiet hours"),
                new DemeritIncident(28,
                        "Littering and/or failure to upkeep cleanliness of common areas / room",
                        1,
                        3,
                        "Common pantry left dirty")
        );

        Person personWithDemerits = new Person(
                BENSON.getName(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getStudentId(),
                BENSON.getRoomNumber(),
                BENSON.getEmergencyContact(),
                BENSON.getRemark(),
                BENSON.getTags(),
                incidents
        );

        JsonAdaptedPerson jsonAdaptedPerson = new JsonAdaptedPerson(personWithDemerits);
        assertEquals(personWithDemerits, jsonAdaptedPerson.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENTID,
                        VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_STUDENTID,
                VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_STUDENTID,
                        VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
                VALID_STUDENTID, VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_STUDENTID,
                        VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_STUDENTID,
                VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidStudentId_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_STUDENTID,
                        VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = StudentId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStudentId_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidRoomNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENTID,
                        INVALID_ROOM_NUMBER, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = RoomNumber.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullRoomNumber_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENTID,
                null, VALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RoomNumber.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmergencyContact_throwsIllegalValueException() {
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENTID,
                        VALID_ROOM_NUMBER, INVALID_EMERGENCY_CONTACT, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = EmergencyContact.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmergencyContact_throwsIllegalValueException() {
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENTID,
                VALID_ROOM_NUMBER, null, VALID_REMARK, VALID_TAGS, List.of());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EmergencyContact.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(null, INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENTID, VALID_ROOM_NUMBER,
                        VALID_EMERGENCY_CONTACT, VALID_REMARK, invalidTags, List.of());
        assertThrows(IllegalValueException.class, person::toModelType);
    }
}
