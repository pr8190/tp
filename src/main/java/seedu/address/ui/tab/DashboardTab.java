package seedu.address.ui.tab;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.person.Person;
import seedu.address.ui.UiPart;

/**
 * DashboardTab displays statistics about the students in the address book.
 */
public class DashboardTab extends UiPart<Region> {
    private static final String FXML = "DashboardTab.fxml";
    private static final String MALE_PRONOUN = "he/him";
    private static final String FEMALE_PRONOUN = "she/her";
    private static final String OTHER_PRONOUN = "they/them";
    private final Logic logic;

    @FXML private Label totalCountLabel;

    @FXML private Label maleCountLabel;
    @FXML private Label femaleCountLabel;
    @FXML private Label otherCountLabel;
    @FXML private Label unknownCountLabel;

    @FXML private Label y1CountLabel;
    @FXML private Label y2CountLabel;
    @FXML private Label y3CountLabel;
    @FXML private Label y4CountLabel;
    @FXML private Label y5CountLabel;
    @FXML private Label y6CountLabel;
    @FXML private Label yMissingCountLabel;

    private Label[] yearLabels = {y1CountLabel, y2CountLabel, y3CountLabel,
        y4CountLabel, y5CountLabel, y6CountLabel};
    /**
     * Creates a {@code DashboardTab} with the given {@code Logic}.
     *
     * @param logic
     */
    public DashboardTab(Logic logic) {
        super(FXML);
        this.logic = logic;
        refresh();
        logic.getFilteredPersonList().addListener((ListChangeListener<Object>) c -> refresh());
    }

    /**
     * Refreshes the statistics displayed on the dashboard
     */
    private void refresh() {
        var list = logic.getFilteredPersonList();
        long totalCount = list.size();

        // ---Gender groups via tags---
        long male = countByTag(list, MALE_PRONOUN);
        long female = countByTag(list, FEMALE_PRONOUN);
        long other = countByTag(list, OTHER_PRONOUN);
        long unknown = totalCount - male - female - other;

        totalCountLabel.setText(String.valueOf(totalCount));
        maleCountLabel.setText(String.valueOf(male));
        femaleCountLabel.setText(String.valueOf(female));
        otherCountLabel.setText(String.valueOf(other));
        unknownCountLabel.setText(String.valueOf(unknown));

        // --- Year groups via tags ---
        long sum = 0;
        for (int i = 0; i < yearLabels.length; i++) {
            long count = countByTag(list, String.valueOf(i + 1));
            sum += count;
            yearLabels[i].setText(String.valueOf(count));
        }
        yMissingCountLabel.setText(String.valueOf(totalCount - sum));
    }

    /**
     * Counts the number of students in the list that have a tag matching the given tag name (case-insensitive).
     * @param list
     * @param tagName
     * @return
     */
    static long countByTag(javafx.collections.ObservableList<? extends Person> list, String tagName) {
        return list.stream()
                .filter(p -> p.getTags().values().stream()
                        .anyMatch(t -> t.getTagContent().equalsIgnoreCase(tagName)))
                .count();
    }
}
