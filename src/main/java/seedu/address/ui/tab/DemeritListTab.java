package seedu.address.ui.tab;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.demerit.DemeritRuleCatalogue;
import seedu.address.ui.UiPart;

/**
 * Demerit Records component of the Student Details Tab.
 */
public class DemeritListTab extends UiPart<Region> {

    private static final String FXML = "DemeritListTab.fxml";

    @FXML
    private TableView<DemeritRecordRow> demeritTableView;

    @FXML
    private TableColumn<DemeritRecordRow, String> indexColumn;

    @FXML
    private TableColumn<DemeritRecordRow, String> descriptionColumn;

    @FXML
    private TableColumn<DemeritRecordRow, String> firstColumn;

    @FXML
    private TableColumn<DemeritRecordRow, String> secondColumn;

    @FXML
    private TableColumn<DemeritRecordRow, String> thirdColumn;

    /**
     * Creates a {@code DemeritRecords} component bound to the selected person.
     */
    public DemeritListTab() {
        super(FXML);
        initialiseColumns();
        populateTable();
    }

    /**
     * Sets up the table columns.
     */
    private void initialiseColumns() {
        indexColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().index()));

        descriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().description()));

        firstColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().first()));

        secondColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().second()));

        thirdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().third()));

        indexColumn.setCellFactory(column -> createCenteredCell());
        firstColumn.setCellFactory(column -> createCenteredCell());
        secondColumn.setCellFactory(column -> createCenteredCell());
        thirdColumn.setCellFactory(column -> createCenteredCell());
        descriptionColumn.setCellFactory(column -> createWrappingCell());

    }

    /**
     * Creates a table cell that wraps long text instead of truncating it with ellipsis.
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
     * Creates a centered cell for short values such as index and points.
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

    private void populateTable() {
        List<DemeritRecordRow> rows = DemeritRuleCatalogue.getAllRules().stream()
                .map(rule -> new DemeritRecordRow(
                        String.valueOf(rule.getIndex()),
                        rule.getTitle(),
                        String.valueOf(rule.getFirstOffencePoints()),
                        String.valueOf(rule.getSecondOffencePoints()),
                        String.valueOf(rule.getThirdAndSubsequentPoints())
                ))
                .toList();

        demeritTableView.getItems().setAll(rows);
    }

    /**
     * Table row model for demerit records.
     */
    private record DemeritRecordRow(String index, String description, String first, String second, String third) {
    }
}
