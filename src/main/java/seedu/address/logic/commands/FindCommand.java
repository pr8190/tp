package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.FilterDetails;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonMatchesDetailsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds persons using one of two methods (case-insensitive).\n"
            + "Method 1: Find by name keywords\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie\n"
            + "\n"
            + "Method 2: Find by multiple attributes. Returns persons that satisfies all attributes. \n"
            + "Parameters: [n=NAME] [p=PHONE] [e=EMAIL] [r=ROOM_NUMBER] [i=STUDENT_ID] [ec=EMERGENCY_CONTACT] [y=YEAR] "
            + "[m=MAJOR] [g=GENDER]\n"
            + "Example: " + COMMAND_WORD + " n/Alice p/91234567 y/1";

    private final Predicate<Person> predicate;
    private final FilterDetails filterDetails;

    private final Logger logger = LogsCenter.getLogger(FindCommand.class);

    /**
     * Creates a FindCommand to find the specified {@code Person} using the given {@code NameContainsKeywordsPredicate}.
     *
     * @param predicate the predicate to be used for finding persons by name.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        this.filterDetails = new FilterDetails();
        this.filterDetails.setNameKeywords(predicate.getKeywords());
    }

    /**
     * Creates a FindCommand to find the specified {@code Person} using the given {@code PersonMatchesDetailsPredicate}.
     *
     * @param predicate the predicate to be used for finding persons by multiple attributes.
     */
    public FindCommand(PersonMatchesDetailsPredicate predicate) {
        this.predicate = predicate;
        this.filterDetails = predicate.filterDetails();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        logger.info("[FIND COMMAND][" + predicate.toString() + "]");
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
