package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FilterDetails;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyFilterDetails;
import seedu.address.model.person.Person;

/**
 * API of the Logic component
 */
public interface Logic {
    // ============= Command Executors =========================================================
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Applies the given filter details and returns the result.
     *
     * @param filterDetails The filter details from the UI.
     * @return the result of applying the filter.
     * @throws CommandException If an error occurs while applying the filter.
     */
    CommandResult executeFilter(FilterDetails filterDetails) throws CommandException;


    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the current read-only filter details.
     *
     * @see seedu.address.model.Model#getFilterDetails()
     */
    ObjectProperty<FilterDetails> getFilterDetailsProperty();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     *
     * @see seedu.address.model.Model#selectedPersonProperty()
     */
    ReadOnlyProperty<Person> selectedPersonProperty();

    /**
     * Sets the selected person in the filtered person list.
     *
     * @see seedu.address.model.Model#setSelectedPerson(Person)
     */
    void setSelectedPerson(Person person);

    ReadOnlyFilterDetails getFilterDetails();
}
