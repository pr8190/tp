package seedu.address.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FilterDetails;
import seedu.address.ui.executors.CommandExecutor;
import seedu.address.ui.executors.FilterExecutor;

/**
 * The Main Window. Provides the basic application layout containing a menu bar and space where other JavaFX elements
 * can be placed. Implements {@link CommandExecutor} and {@link FilterExecutor} to handle user commands
 * and filter operations.
 */
public class MainWindow extends UiPart<Stage> implements CommandExecutor, FilterExecutor {

    private static final String FXML = "MainWindow.fxml";
    private static final String MESSAGE_DELETE_CANCELLED = "Deletion cancelled.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;
    private final HelpWindow helpWindow;
    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane tabSectionPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane listSectionPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given stage and logic component.
     *
     * @param primaryStage primary stage of the application
     * @param logic        logic component used to execute commands and retrieve state
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow(primaryStage);
    }

    /**
     * Sets the initial size and location of the main window using the given GUI settings.
     *
     * @param guiSettings saved GUI settings
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        // If the saved window coordinates are not visible, move the window to the primary screen
        // This can happen when the screen resolution is changed or when the app is opened on a different monitor
        if (hasVisibleWindowCoordinates(guiSettings)) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        } else {
            moveWindowToPrimaryScreen();
        }
        primaryStage.setMaximized(true);
    }

    /**
     * Registers keyboard accelerators for supported menu items.
     */
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Returns whether the saved window coordinates are visible on one of the current screens.
     *
     * @param guiSettings saved GUI settings
     * @return true if the saved coordinates are visible
     */
    private boolean hasVisibleWindowCoordinates(GuiSettings guiSettings) {
        if (guiSettings.getWindowCoordinates() == null) {
            return false;
        }

        return isCoordinateVisible(guiSettings.getWindowCoordinates().getX(),
                guiSettings.getWindowCoordinates().getY());
    }

    /**
     * Moves the main window to the primary screen.
     */
    private void moveWindowToPrimaryScreen() {
        Rectangle2D primaryBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryBounds.getMinX());
        primaryStage.setY(primaryBounds.getMinY());
    }

    /**
     * Sets the accelerator of a menu item and ensures it still works
     * when focus is inside a text input control.
     *
     * @param menuItem menu item receiving the accelerator
     * @param keyCombination accelerator key combination
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Returns whether the specified screen coordinate is visible on any screen.
     *
     * @param x x-coordinate to check
     * @param y y-coordinate to check
     * @return true if the coordinate is visible
     */
    private boolean isCoordinateVisible(double x, double y) {
        return Screen.getScreens().stream()
                .map(Screen::getVisualBounds)
                .anyMatch(bounds -> bounds.contains(x, y));
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills all placeholders in this window with their corresponding UI parts.
     */
    void fillInnerParts() {
        ListSection listSection = new ListSection(logic, this);
        listSectionPlaceholder.getChildren().add(listSection.getRoot());

        TabSection tabSection = new TabSection(logic);
        tabSectionPlaceholder.getChildren().add(tabSection.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Applies the given filter details and updates the shared result display.
     *
     * @param filterDetails filter details entered from the UI
     * @return the result of applying the filter
     * @throws CommandException if the filter operation fails
     */
    @Override
    public CommandResult executeFilter(FilterDetails filterDetails) throws CommandException {
        try {
            CommandResult commandResult = logic.executeFilter(filterDetails);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            return commandResult;
        } catch (CommandException e) {
            logger.info("An error occurred while applying filters: " + filterDetails);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Executes the given command text and updates the UI based on the result.
     *
     * @param commandText raw command entered by the user
     * @return the result of executing the command
     * @throws CommandException if command execution fails
     * @throws ParseException   if command parsing fails
     */
    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        try {
            if (logic.requiresConfirmation(commandText) && !showConfirmationDialog()) {
                CommandResult cancelResult = new CommandResult(MESSAGE_DELETE_CANCELLED);
                logger.info("Result: " + cancelResult.getFeedbackToUser());
                resultDisplay.setFeedbackToUser(cancelResult.getFeedbackToUser());
                return cancelResult;
            }

            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Shows a confirmation dialog before deleting or clearing resident(s).
     *
     * @return true if the user confirms the action
     */
    private boolean showConfirmationDialog() {
        ButtonType confirmButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(primaryStage);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Are you sure you want to perform this action?");
        alert.setContentText("Alternatively, press 'Enter' to confirm or 'Escape' to cancel.");
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == confirmButton;
    }

    /**
     * Opens the help window, or focuses it if it is already open.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Saves the current GUI settings and closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Shows the main application window.
     */
    void show() {
        primaryStage.show();
    }
}
