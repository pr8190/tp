package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public String getMessageSuccess(int numberOfPersons) {
        return "All " + numberOfPersons + " resident(s) have been removed from the Hall Ledger";
    }

    public int getNumberOfPersons(Model model) {
        assert model != null : "Model should not be null";

        return model.getAddressBook().getPersonList().size();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        int numberOfPersons = getNumberOfPersons(model);

        model.setAddressBook(new AddressBook());
        model.showAllPersons();

        return new CommandResult(getMessageSuccess(numberOfPersons));
    }
}
