package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.FilterDetails;
import seedu.address.model.Model;
import seedu.address.model.person.PersonMatchesDetailsPredicate;

/**
 * Finds and lists all persons in the address book that match the given filter details.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds persons by prefixes (case-insensitive). Returns residents that satisfy all"
            + " specified prefixes.\n"
            + "Parameters: [n=NAME] [p=PHONE] [e=EMAIL] [r=ROOM_NUMBER] [i=STUDENT_ID] [ec=EMERGENCY_CONTACT] [y=YEAR] "
            + "[m=MAJOR] [g=GENDER]\n"
            + "Example: " + COMMAND_WORD + " n=Alice p=91234567 y=1";

    private final FilterDetails filterDetails;
    private final PersonMatchesDetailsPredicate predicate;
    private final String warningMessage;

    /**
     * Creates a {@code FindCommand} using the given {@code FilterDetails}.
     */
    public FindCommand(FilterDetails filterDetails) {
        this(filterDetails, "");
    }

    /**
     * Creates a {@code FindCommand} using the given {@code FilterDetails} and optional warning message.
     */
    public FindCommand(FilterDetails filterDetails, String warningMessage) {
        requireNonNull(warningMessage);
        this.filterDetails = new FilterDetails(filterDetails);
        this.predicate = new PersonMatchesDetailsPredicate(this.filterDetails);
        this.warningMessage = warningMessage;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        Logger logger = LogsCenter.getLogger(FindCommand.class);
        logger.info("[FIND COMMAND][" + predicate + "]");

        model.setFilterDetails(filterDetails);
        model.updateFilteredPersonList(predicate);
        String resultMessage = String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList()
                .size());

        if (!warningMessage.isEmpty()) {
            resultMessage = resultMessage + "\n" + warningMessage;
        }

        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate)
                && warningMessage.equals(otherFindCommand.warningMessage);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("warningMessage", warningMessage)
                .toString();
    }
}
