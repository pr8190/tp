package seedu.address.ui.executors;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FilterDetails;

/**
 * Represents a function that can apply filters and return a command-like result.
 */
@FunctionalInterface
public interface FilterExecutor {
    CommandResult execute(FilterDetails filterDetails) throws CommandException;
}
