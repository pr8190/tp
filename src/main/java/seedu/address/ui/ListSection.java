package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.logic.Logic;

/**
 * UI for the ListSection that is displayed on the right hand side of the main
 * window.
 */
public class ListSection extends UiPart<Region> {

    private static final String FXML = "ListSection.fxml";

    private final Logic logic;
    private final FilterPanel.FilterExecutor filterExecutor;

    @FXML
    private StackPane filterPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    /**
     * Creates a {@code ListSection} with the given {@code Logic}.
     *
     * @param logic
     */
    public ListSection(Logic logic, FilterPanel.FilterExecutor filterExecutor) {
        super(FXML);
        this.logic = logic;
        this.filterExecutor = filterExecutor;
        fillInnerParts();
    }

    private void fillInnerParts() {
        FilterPanel filterPanel = new FilterPanel(logic.getFilterDetailsProperty(), filterExecutor);
        filterPanelPlaceholder.getChildren().add(filterPanel.getRoot());

        PersonListPanel personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
}
