package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.util.ModelUtil.getPersonByStudentIdOrThrow;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
 * Edits the details of an existing resident in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited resident: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the resident identified "
            + "by the StudentID used in the displayed resident list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: i=STUDENT_ID (must be a valid student ID) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_STUDENT_ID + "STUDENT_ID] "
            + "[" + PREFIX_ROOM_NUMBER + "ROOM] "
            + "[" + PREFIX_EMERGENCY_CONTACT + "EMERGENCY_CONTACT] \n"
            + "Example: " + COMMAND_WORD + " i=A0123456X "
            + PREFIX_PHONE + "+65 91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_DUPLICATE_PERSON = "The edit details cause duplicated resident details "
            + "in the address book.";
    public static final String MESSAGE_ROOM_OCCUPIED = "This room is already occupied!";
    public static final String MESSAGE_DUPLICATE_STUDENT_ID_PREFIX = "Please ensure that there are at most two "
            + PREFIX_STUDENT_ID + "prefixes, the first indicates the Student ID of the resident to edit, the "
            + "second indicates the resident's edited student ID value.\n%s";

    private final StudentId targetStudentId;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param targetStudentId student id of the resident in the filtered person list to edit
     * @param editPersonDescriptor details to edit the resident with
     */
    public EditCommand(StudentId targetStudentId, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(targetStudentId);
        requireNonNull(editPersonDescriptor);

        this.targetStudentId = targetStudentId;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToEdit = getPersonByStudentIdOrThrow(model, targetStudentId);

        assert editPersonDescriptor != null;

        if (editPersonDescriptor.getRoomNumber().isPresent()) {
            RoomNumber newRoom = editPersonDescriptor.getRoomNumber().get();
            boolean roomTakenByOther = model.getPersonByRoomNumber(newRoom)
                    .filter(occupant -> !occupant.isSamePerson(personToEdit)) // allow same person to "keep" their room
                    .isPresent();

            if (roomTakenByOther) {
                throw new CommandException(MESSAGE_ROOM_OCCUPIED);
            }
        }

        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setPerson(personToEdit, editedPerson);
        model.showAllPersons();
        model.setSelectedPerson(editedPerson);

        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, Messages.format(editedPerson)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        StudentId updatedStudentId = editPersonDescriptor.getStudentId().orElse(personToEdit.getStudentId());
        RoomNumber updatedRoomNumber = editPersonDescriptor.getRoomNumber().orElse(personToEdit.getRoomNumber());
        EmergencyContact updatedEmergencyContact = editPersonDescriptor.getEmergencyContact()
                .orElse(personToEdit.getEmergencyContact());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());

        return new Person(
                updatedName,
                updatedPhone,
                updatedEmail,
                updatedStudentId,
                updatedRoomNumber,
                updatedEmergencyContact,
                updatedRemark,
                personToEdit.getTags(), // Tags are not editable through EditCommand, so we keep the original tags
                personToEdit.getDemeritIncidents()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand otherEditCommand)) {
            return false;
        }

        return targetStudentId.equals(otherEditCommand.targetStudentId)
                && editPersonDescriptor.equals(otherEditCommand.editPersonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", targetStudentId)
                .add("editPersonDescriptor", editPersonDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private StudentId studentId;
        private RoomNumber roomNumber;
        private EmergencyContact emergencyContact;
        private Remark remark;
        private HashMap<TagType, Tag> tags;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setStudentId(toCopy.studentId);
            setRoomNumber(toCopy.roomNumber);
            setEmergencyContact(toCopy.emergencyContact);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, studentId, roomNumber,
                    emergencyContact, remark, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setStudentId(StudentId studentId) {
            this.studentId = studentId;
        }

        public Optional<StudentId> getStudentId() {
            return Optional.ofNullable(studentId);
        }

        public void setRoomNumber(RoomNumber roomNumber) {
            this.roomNumber = roomNumber;
        }

        public Optional<RoomNumber> getRoomNumber() {
            return Optional.ofNullable(roomNumber);
        }

        public void setEmergencyContact(EmergencyContact emergencyContact) {
            this.emergencyContact = emergencyContact;
        }

        public Optional<EmergencyContact> getEmergencyContact() {
            return Optional.ofNullable(emergencyContact);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }


        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor otherEditPersonDescriptor)) {
                return false;
            }

            return Objects.equals(name, otherEditPersonDescriptor.name)
                    && Objects.equals(phone, otherEditPersonDescriptor.phone)
                    && Objects.equals(email, otherEditPersonDescriptor.email)
                    && Objects.equals(studentId, otherEditPersonDescriptor.studentId)
                    && Objects.equals(roomNumber, otherEditPersonDescriptor.roomNumber)
                    && Objects.equals(emergencyContact, otherEditPersonDescriptor.emergencyContact)
                    && Objects.equals(remark, otherEditPersonDescriptor.remark)
                    && Objects.equals(tags, otherEditPersonDescriptor.tags);
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
                    .toString();
        }
    }
}

