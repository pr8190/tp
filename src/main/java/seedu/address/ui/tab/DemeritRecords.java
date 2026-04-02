package seedu.address.ui.tab;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.demerit.DemeritIncident;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * Demerit Records component of the Student Details Tab.
 */
public class DemeritRecords extends UiPart<Region> {

    private static final String FXML = "DemeritRecords.fxml";

    private static final String BODY_CELL_STYLE =
            "-fx-background-color: #2b2b2b; "
                    + "-fx-border-color: #555555; "
                    + "-fx-text-fill: white;";

    private static final String WRAPPED_TEXT_STYLE =
            "-fx-fill: white;";

    private final ObservableValue<Person> selectedPerson;

    @FXML
    private TableView<DemeritRecordRow> demeritTableView;

    @FXML
    private TableColumn<DemeritRecordRow, String> indexColumn;

    @FXML
    private TableColumn<DemeritRecordRow, String> descriptionColumn;

    @FXML
    private TableColumn<DemeritRecordRow, String> remarkColumn;

    @FXML
    private TableColumn<DemeritRecordRow, String> pointsColumn;

    /**
     * Creates a {@code DemeritRecords} component bound to the selected person.
     */
    public DemeritRecords(ObservableValue<Person> selectedPerson) {
        super(FXML);
        this.selectedPerson = selectedPerson;
        initialiseColumns();
        bindSelectionListener();
        updateTable(selectedPerson.getValue());
    }

    /**
     * Sets up the table columns.
     */
    private void initialiseColumns() {
        indexColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().index()));

        descriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().description()));

        remarkColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().remark()));

        pointsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().points()));

        indexColumn.setCellFactory(column -> createCenteredCell());
        pointsColumn.setCellFactory(column -> createCenteredCell());
        descriptionColumn.setCellFactory(column -> createWrappingCell());
        remarkColumn.setCellFactory(column -> createWrappingCell());
    }

    /**
     * Creates a table cell that wraps long text instead of truncating it with ellipsis.
     */
    private TableCell<DemeritRecordRow, String> createWrappingCell() {
        return new TableCell<>() {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(widthProperty().subtract(16));
                text.setStyle(WRAPPED_TEXT_STYLE);
                setGraphic(text);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setPrefHeight(Region.USE_COMPUTED_SIZE);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    text.setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    text.setText(item);
                    setGraphic(text);
                    setStyle(BODY_CELL_STYLE);
                }
            }
        };
    }

    /**
     * Creates a centered white-text cell for short values such as index and points.
     */
    private TableCell<DemeritRecordRow, String> createCenteredCell() {
        return new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    setText(item);
                    setStyle(BODY_CELL_STYLE + " -fx-alignment: CENTER; -fx-text-fill: white;");
                }
            }
        };
    }

    /**
     * Updates the table whenever the selected person changes.
     */
    private void bindSelectionListener() {
        selectedPerson.addListener((observable, oldValue, newValue) -> updateTable(newValue));
    }

    /**
     * Rebuilds the table rows for the given person.
     */
    private void updateTable(Person person) {
        demeritTableView.getItems().clear();

        if (person == null) {
            return;
        }

        List<DemeritRecordRow> rows = new ArrayList<>();
        List<DemeritIncident> incidents = person.getDemeritIncidents();

        for (int i = 0; i < incidents.size(); i++) {
            DemeritIncident incident = incidents.get(i);
            rows.add(new DemeritRecordRow(
                    String.valueOf(i + 1),
                    formatDescription(incident),
                    incident.getRemark(),
                    "+" + incident.getPointsApplied()
            ));
        }

        demeritTableView.getItems().setAll(rows);
    }

    /**
     * Formats a readable description for one demerit incident.
     */
    private String formatDescription(DemeritIncident incident) {
        return String.format("[%d] %s (offence %d)",
                incident.getRuleIndex(),
                incident.getRuleTitle(),
                incident.getOffenceNumber());
    }

    /**
     * Table row model for demerit records.
     */
    private record DemeritRecordRow(String index, String description, String remark, String points) {
    }
}
