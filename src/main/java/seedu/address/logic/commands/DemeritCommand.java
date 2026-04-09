package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.util.ModelUtil.getPersonByStudentIdOrThrow;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.demerit.DemeritIncident;
import seedu.address.model.demerit.DemeritRule;
import seedu.address.model.demerit.DemeritRuleCatalogue;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * Applies a demerit rule to a resident identified by student ID.
 */
public class DemeritCommand extends Command {

    public static final String COMMAND_WORD = "demerit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Applies an indexed demerit rule to a resident.\n"
            + "Format: " + COMMAND_WORD + " i=STUDENT_ID di=RULE_INDEX [rm=REMARK]\n"
            + "Example: " + COMMAND_WORD + " i=A1234567X di=18 rm=Visitor stayed during quiet hours";

    public static final String MESSAGE_STUDENT_NOT_FOUND =
            "ResidentNotFound: No resident found with student ID %s.";
    public static final String MESSAGE_RULE_NOT_FOUND =
            "No demerit rule found with index %d.";
    public static final String MESSAGE_ADD_DEMERIT_SUCCESS =
            "Added demerit to resident: %1$s%nRule: [%2$d] %3$s%nRemark: %4$s%nPoints added: %5$d"
                    + "%nTotal demerit points: %6$d";

    private final StudentId targetStudentId;
    private final int ruleIndex;
    private final String remark;

    /**
     * Creates a command that applies the specified demerit rule to the target resident.
     *
     * @param targetStudentId student ID of the resident receiving the demerit
     * @param ruleIndex index of the demerit rule to apply
     * @param remark optional remark recorded together with the incident
     */
    public DemeritCommand(StudentId targetStudentId, int ruleIndex, String remark) {
        requireNonNull(targetStudentId);
        requireNonNull(remark);

        this.targetStudentId = targetStudentId;
        this.ruleIndex = ruleIndex;
        this.remark = remark;
    }

    /**
     * Applies the specified demerit rule to the target resident and updates the model.
     *
     * @param model the model used to retrieve and update resident data
     * @return the result message after the demerit has been applied
     * @throws CommandException if the target resident or demerit rule cannot be found
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToUpdate = getPersonByStudentIdOrThrow(model, targetStudentId);

        DemeritRule rule = DemeritRuleCatalogue.findByIndex(ruleIndex)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_RULE_NOT_FOUND, ruleIndex)));

        int priorOccurrences = personToUpdate.getOccurrenceCountForRule(ruleIndex);
        int offenceNumber = priorOccurrences + 1;
        int pointsApplied = rule.getPointsForOccurrence(offenceNumber);

        DemeritIncident newIncident = new DemeritIncident(
                rule.getIndex(),
                rule.getTitle(),
                offenceNumber,
                pointsApplied,
                remark
        );

        List<DemeritIncident> updatedIncidents = new ArrayList<>(personToUpdate.getDemeritIncidents());
        updatedIncidents.add(newIncident);

        Person updatedPerson = new Person(
                personToUpdate.getName(),
                personToUpdate.getPhone(),
                personToUpdate.getEmail(),
                personToUpdate.getStudentId(),
                personToUpdate.getRoomNumber(),
                personToUpdate.getEmergencyContact(),
                personToUpdate.getRemark(),
                personToUpdate.getTags(),
                updatedIncidents
        );

        model.setPerson(personToUpdate, updatedPerson);
        model.showAllPersons();
        model.setSelectedPerson(updatedPerson);

        return new CommandResult(String.format(
                MESSAGE_ADD_DEMERIT_SUCCESS,
                Messages.format(updatedPerson),
                rule.getIndex(),
                rule.getTitle(),
                newIncident.getRemark().isEmpty() ? "-" : newIncident.getRemark(),
                pointsApplied,
                updatedPerson.getTotalDemeritPoints()
        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DemeritCommand otherCommand)) {
            return false;
        }

        return targetStudentId.equals(otherCommand.targetStudentId)
                && ruleIndex == otherCommand.ruleIndex
                && remark.equals(otherCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetStudentId", targetStudentId)
                .add("ruleIndex", ruleIndex)
                .add("remark", remark)
                .toString();
    }
}
