package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_EMPTY = "The Hall Ledger is already empty!";
    public static final String MESSAGE_SUCCESS = "All %d resident(s) have been removed from the Hall Ledger";

    public String getMessageSuccess(int numberOfPersons) {
        return String.format(MESSAGE_SUCCESS, numberOfPersons);
    }

    public int getNumberOfPersons(Model model) {
        assert model != null : "Model should not be null";

        return model.getAddressBook().getPersonList().size();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int numberOfPersons = getNumberOfPersons(model);
        if (numberOfPersons == 0) {
            throw new CommandException(MESSAGE_EMPTY);
        }

        model.setAddressBook(new AddressBook());
        model.showAllPersons();

        return new CommandResult(getMessageSuccess(numberOfPersons));
    }
}
