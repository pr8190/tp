package seedu.address.ui.tab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.tag.TagType;
import seedu.address.model.util.PersonBuilder;

/**
 * Test class for DashboardTab.
 */
public class DashboardTabTest {

    @Test
    public void countByTag_emptyList_returnsZero() {
        ObservableList<Person> list = FXCollections.observableArrayList();
        assertEquals(0, DashboardTab.countByTag(list, "1"));
    }

    @Test
    public void countByTag_matchingYearTag_returnsCorrectCount() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "2"}).build()
        );
        assertEquals(2, DashboardTab.countByTag(list, "1"));
    }

    @Test
    public void countByTag_matchingGenderTag_returnsCorrectCount() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "he/him"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "he/him"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "she/her"}).build()
        );
        assertEquals(2, DashboardTab.countByTag(list, "he/him"));
        assertEquals(1, DashboardTab.countByTag(list, "she/her"));
    }

    @Test
    public void countByTag_caseInsensitive_returnsCorrectCount() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "1"}).build()
        );
        assertEquals(2, DashboardTab.countByTag(list, "1"));
    }

    @Test
    public void countByTag_noMatchingTag_returnsZero() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.GENDER, "he/him"}).build()
        );
        assertEquals(0, DashboardTab.countByTag(list, "1"));
    }

    @Test
    public void countByTag_allYearGroups_returnsCorrectCounts() {
        ObservableList<Person> list = FXCollections.observableArrayList(
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "1"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "2"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "3"}).build(),
                new PersonBuilder().withTags(new Object[]{TagType.YEAR, "4"}).build()
        );
        assertEquals(1, DashboardTab.countByTag(list, "1"));
        assertEquals(1, DashboardTab.countByTag(list, "2"));
        assertEquals(1, DashboardTab.countByTag(list, "3"));
        assertEquals(1, DashboardTab.countByTag(list, "4"));
    }
}
