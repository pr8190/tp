package seedu.address.model.util;

import java.util.HashMap;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new StudentId("A0485321Y"), new RoomNumber("4-A"),
                        new EmergencyContact("98765432"), new Remark(""), new HashMap<>()),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new StudentId("A1123456Z"), new RoomNumber("15-R"),
                        new EmergencyContact("91234567"), new Remark(""), new HashMap<>()),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new StudentId("A1246354T"), new RoomNumber("3-D"),
                        new EmergencyContact("87654321"), new Remark(""), new HashMap<>()),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new StudentId("A0148321W"), new RoomNumber("10-C"),
                        new EmergencyContact("12345678"), new Remark(""), new HashMap<>()),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new StudentId("A1436528Q"), new RoomNumber("5-B"),
                        new EmergencyContact("56789012"), new Remark(""), new HashMap<>()),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new StudentId("A0246835Z"), new RoomNumber("12-D"),
                        new EmergencyContact("23456789"), new Remark(""), new HashMap<>())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static HashMap<TagType, Tag> getTagSet(Object[]... tags) {
        HashMap<TagType, Tag> tagMap = new HashMap<>();
        for (Object[] pair : tags) {
            TagType type = TagType.valueOf(pair[0].toString());
            String tagName = pair[1].toString();
            tagMap.put(type, new Tag(type, tagName));
        }
        return tagMap;
    }
}
