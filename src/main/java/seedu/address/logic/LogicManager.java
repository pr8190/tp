package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FilterDetails;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyFilterDetails;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    // ========================= Command Executor ===============================================

    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException if an error occurs during command execution.
     * @throws ParseException if an error occurs during parsing the command.
     */
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        Command command = addressBookParser.parseCommand(commandText);
        CommandResult commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        return commandResult;
    }

    /**
     * Executes the filter command and returns the result. Calls on {@code FindCommand} to perform the filtering based
     * on the {@link FilterDetails} called.
     *
     * @param filterDetails The filter details.
     * @return the result of executing the find command using the filter details.
     * @throws CommandException if an error occurs during command execution.
     */
    @Override
    public CommandResult executeFilter(FilterDetails filterDetails) throws CommandException {
        assert (filterDetails != null);

        try {
            filterDetails.validateKeywordLimits();
        } catch (IllegalArgumentException exception) {
            throw new CommandException(exception.getMessage(), exception);
        }

        logger.info("----------------[FILTER DETAILS FROM UI] " + filterDetails);
        Command command = new FindCommand(filterDetails);
        return command.execute(model);
    }

    @Override
    public boolean requiresConfirmation(String commandText) {
        try {
            Command command = addressBookParser.parseCommand(commandText);
            if (command instanceof DeleteCommand deleteCommand) {
                return model.getAddressBook().getPersonList().stream()
                        .anyMatch(person -> person.getStudentId().equals(deleteCommand.getTargetStudentId()));
            }
            if (command instanceof ClearCommand) {
                return !model.getAddressBook().getPersonList().isEmpty();
            }
            return false;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ReadOnlyFilterDetails getFilterDetails() {
        return model.getFilterDetails();
    }

    @Override
    public ReadOnlyProperty<Person> selectedPersonProperty() {
        return model.selectedPersonProperty();
    }

    @Override
    public void setSelectedPerson(Person person) {
        model.setSelectedPerson(person);
    }
}
