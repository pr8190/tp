package seedu.address.ui.tab;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.demerit.DemeritIncident;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * Displays the selected resident's total demerit points and demerit incident history.
 */
public class DemeritRecords extends UiPart<Region> {

    private static final String FXML = "DemeritRecords.fxml";
    private static final String TOTAL_POINTS_LABEL_PREFIX = "Total Demerit Points: ";

    private final ObservableValue<Person> selectedPerson;

    @FXML
    private Label totalPointsLabel;

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
     * Creates a demerit records panel bound to the currently selected resident.
     *
     * @param selectedPerson observable selected resident
     */
    public DemeritRecords(ObservableValue<Person> selectedPerson) {
        super(FXML);
        this.selectedPerson = selectedPerson;
        initialiseColumns();
        bindSelectionListener();
        updateView(selectedPerson.getValue());
    }

    /**
     * Initialises the table columns and their corresponding cell factories.
     */
    private void initialiseColumns() {
        indexColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().index()));
        indexColumn.setComparator(Comparator.comparingInt(Integer::parseInt));

        descriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().description()));

        remarkColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().remark()));

        pointsColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().points()));
        pointsColumn.setComparator(Comparator.comparingInt(points -> Integer.parseInt(points.replace("+", ""))));

        indexColumn.setCellFactory(column -> createCenteredCell());
        pointsColumn.setCellFactory(column -> createCenteredCell());
        descriptionColumn.setCellFactory(column -> createWrappingCell());
        remarkColumn.setCellFactory(column -> createWrappingCell());
    }

    /**
     * Returns a table cell that wraps long text instead of truncating it.
     *
     * @return a wrapping table cell
     */
    private TableCell<DemeritRecordRow, String> createWrappingCell() {
        return new TableCell<>() {
            private final Text text = new Text();

            {
                getStyleClass().add("demerit-record-body-cell");
                text.wrappingWidthProperty().bind(widthProperty().subtract(16));
                text.getStyleClass().add("demerit-record-wrapped-text");
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
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        };
    }

    /**
     * Returns a centered table cell for short values such as row numbers and point totals.
     *
     * @return a centered table cell
     */
    private TableCell<DemeritRecordRow, String> createCenteredCell() {
        return new TableCell<>() {
            {
                getStyleClass().addAll("demerit-record-body-cell", "demerit-record-centered-cell");
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                }
            }
        };
    }

    /**
     * Binds the panel to changes in the selected resident.
     */
    private void bindSelectionListener() {
        selectedPerson.addListener((observable, oldValue, newValue) -> updateView(newValue));
    }

    /**
     * Updates the summary label and incident table for the given resident.
     *
     * @param person currently selected resident
     */
    private void updateView(Person person) {
        updateTotalPointsLabel(person);
        updateTable(person);
    }

    /**
     * Updates the total demerit points label for the given resident.
     *
     * @param person currently selected resident
     */
    private void updateTotalPointsLabel(Person person) {
        int totalPoints = person == null ? 0 : person.getTotalDemeritPoints();
        totalPointsLabel.setText(TOTAL_POINTS_LABEL_PREFIX + totalPoints);
    }

    /**
     * Rebuilds the demerit incident table for the given resident.
     *
     * @param person currently selected resident
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
     * Returns a readable description for the given demerit incident.
     *
     * @param incident incident to describe
     * @return formatted incident description
     */
    private String formatDescription(DemeritIncident incident) {
        return String.format("[%d] %s (offence %d)",
                incident.getRuleIndex(),
                incident.getRuleTitle(),
                incident.getOffenceNumber());
    }

    /**
     * Represents one row in the demerit records table.
     */
    private record DemeritRecordRow(String index, String description, String remark, String points) {
    }
}
