package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROOM_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MAJOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_YEAR;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagType;
import seedu.address.storage.JsonAddressBookStorage;

/**
 * A list of person data in the correct format
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withStudentId("A1234567X").withEmail("alice@example.com")
            .withPhone("+65 94351253")
            .withRoomNumber("13E").withEmergencyContact("+65 91234567")
            .withRemark("Allergic to peanuts")
            .withTags(new Object[]{TagType.GENDER, "Female"},
                    new Object[]{TagType.YEAR, "Y1"},
                    new Object[]{TagType.MAJOR, "CS"}).build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withStudentId("A8765432Y").withEmail("johnd@example.com").withPhone("+65 98765432")
            .withRoomNumber("10A").withEmergencyContact("+65 91234567")
            .withRemark("Allergic to peanuts")
            .withTags(new Object[]{TagType.MAJOR, "CS Maths"},
                    new Object[]{TagType.YEAR, "Y2"},
                    new Object[]{TagType.GENDER, "Male"}).build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("+65 95352563")
            .withEmail("heinz@example.com").withStudentId("A1111111X").withRoomNumber("12B")
            .withEmergencyContact("+65 91234567").build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("+65 87652533")
            .withEmail("cornelia@example.com").withStudentId("A2222222X").withRoomNumber("6C")
            .withEmergencyContact("+65 91234567")
            .withTags(new Object[]{TagType.YEAR, "Y3"},
                    new Object[]{TagType.MAJOR, "Business"}).build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("+65 9482224")
            .withEmail("werner@example.com").withStudentId("A3333333X").withRoomNumber("7E")
            .withEmergencyContact("+65 91234567")
            .withTags(new Object[]{TagType.YEAR, "Y1"},
                    new Object[]{TagType.GENDER, "Female"}).build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("+65 9482427")
            .withEmail("lydia@example.com").withStudentId("A4444444X").withRoomNumber("14A")
            .withEmergencyContact("+65 91234567")
            .withTags(new Object[]{TagType.MAJOR, "Economics Statistics"},
                    new Object[]{TagType.GENDER, "Female"}).build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("+65 9482442")
            .withEmail("anna@example.com").withStudentId("A5555555X").withRoomNumber("15N")
            .withEmergencyContact("+65 91234567")
            .withTags(new Object[]{TagType.YEAR, "Y4"},
                    new Object[]{TagType.GENDER, "NB"}).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("+65 8482424")
            .withEmail("stefan@example.com").withStudentId("A6666666X").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("+65 8482131")
            .withEmail("hans@example.com").withStudentId("A7777777X").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withStudentId(VALID_STUDENTID_AMY).withRoomNumber(VALID_ROOM_NUMBER_AMY)
            .withEmergencyContact(VALID_EMERGENCY_CONTACT_AMY).withTags(VALID_TAG_YEAR).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withStudentId(VALID_STUDENTID_BOB).withRoomNumber(VALID_ROOM_NUMBER_BOB)
            .withEmergencyContact(VALID_EMERGENCY_CONTACT_BOB).withTags(VALID_TAG_MAJOR, VALID_TAG_YEAR).build();


    public static final String KEYWORD_MATCHING_MEIER = "Meier";

    private TypicalPersons() {}

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
            System.out.println(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /** Regenerates the typical persons JSON file. This allows easy updating of typicalPersonsAddressBook. */
    public static void main(String[] args) throws IOException {
        new JsonAddressBookStorage(Paths.get(
                "src", "test", "data", "JsonSerializableAddressBookTest",
                "typicalPersonsAddressBook.json"))
                .saveAddressBook(getTypicalAddressBook());
        System.out.println("Done.");
    }
}
