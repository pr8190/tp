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
            + ": Finds persons by prefixes (case-insensitive). Returns persons that satisfy all"
            + " specified prefixes.\n"
            + "Parameters: [n=NAME] [p=PHONE] [e=EMAIL] [r=ROOM_NUMBER] [i=STUDENT_ID] [ec=EMERGENCY_CONTACT] [y=YEAR] "
            + "[m=MAJOR] [g=GENDER]\n"
            + "Example: " + COMMAND_WORD + " n=Alice p=91234567 y=Y1";

    private final FilterDetails filterDetails;
    private final PersonMatchesDetailsPredicate predicate;

    private final Logger logger = LogsCenter.getLogger(FindCommand.class);

    /**
     * Creates a {@code FindCommand} using the given {@code FilterDetails}.
     */
    public FindCommand(FilterDetails filterDetails) {
        this.filterDetails = new FilterDetails(filterDetails);
        this.predicate = new PersonMatchesDetailsPredicate(this.filterDetails);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("[FIND COMMAND][" + predicate + "]");
        model.setFilterDetails(filterDetails);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
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

        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
