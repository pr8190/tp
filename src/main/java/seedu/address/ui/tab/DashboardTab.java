package seedu.address.ui.tab;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

public class DashboardTab extends UiPart<Region> {
    private static final String FXML = "DashboardTab.fxml";
    private final Logic logic;

    @FXML private Label maleCountLabel;
    @FXML private Label femaleCountLabel;
    @FXML private Label otherCountLabel;

    @FXML private Label y1CountLabel;
    @FXML private Label y2CountLabel;
    @FXML private Label y3CountLabel;
    @FXML private Label y4CountLabel;

    public DashboardTab(Logic logic) {
        super(FXML, new Region());
        this.logic = logic;
        refresh();
        logic.getFilteredPersonList().addListener((ListChangeListener<Object>) c -> refresh());
    }

    private void refresh() {
        var list = logic.getFilteredPersonList();

        // ---Gender groups via tags---
        long male   = countByTag(list, "male");
        long female = countByTag(list, "female");
        long other  = list.size() - male - female;

        maleCountLabel.setText(String.valueOf(male));
        femaleCountLabel.setText(String.valueOf(female));
        otherCountLabel.setText(String.valueOf(other));

        // --- Year groups via tags ---
        y1CountLabel.setText(String.valueOf(countByTag(list, "Y1")));
        y2CountLabel.setText(String.valueOf(countByTag(list, "Y2")));
        y3CountLabel.setText(String.valueOf(countByTag(list, "Y3")));
        y4CountLabel.setText(String.valueOf(countByTag(list, "Y4")));
    }

    private long countByTag(javafx.collections.ObservableList<? extends Person> list, String tagName) {
        return list.stream()
                .filter(p -> p.getTags().values().stream()
                        .anyMatch(t -> t.tagName.equalsIgnoreCase(tagName)))
                .count();
    }
}