package seedu.address.ui.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagType;
import seedu.address.testutil.PersonBuilder;

/**
 * Test class for DashboardTab.
 */
public class DashboardTabTest {

    @Test
    public void countByTag_emptyList_returnsZero() {
        ObservableList<Person> list = FXCollections.observableArrayList();
        assertEquals(0, DashboardTab.countByTag(list, "Y1"));
    }

    @Test
    public void countByTag_matchingYearTag_returnsCorrectCount() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y2"}).build()
        );
        assertEquals(2, DashboardTab.countByTag(list, "Y1"));
    }

    @Test
    public void countByTag_matchingGenderTag_returnsCorrectCount() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "he"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "he"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "she"}).build()
        );
        assertEquals(2, DashboardTab.countByTag(list, "he"));
        assertEquals(1, DashboardTab.countByTag(list, "she"));
    }

    @Test
    public void countByTag_caseInsensitive_returnsCorrectCount() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "y1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y1"}).build()
        );
        assertEquals(2, DashboardTab.countByTag(list, "Y1"));
    }

    @Test
    public void countByTag_noMatchingTag_returnsZero() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "he"}).build()
        );
        assertEquals(0, DashboardTab.countByTag(list, "Y1"));
    }

    @Test
    public void countByTag_allYearGroups_returnsCorrectCounts() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y2"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y3"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "Y4"}).build()
        );
        assertEquals(1, DashboardTab.countByTag(list, "Y1"));
        assertEquals(1, DashboardTab.countByTag(list, "Y2"));
        assertEquals(1, DashboardTab.countByTag(list, "Y3"));
        assertEquals(1, DashboardTab.countByTag(list, "Y4"));
    }
}
