package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    // EP: null tag type
    public void constructor_nullTagType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null, "valid"));
    }

    // EP: null tag content
    @Test
    public void constructor_nullTagContent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(TagType.MAJOR, null));
    }

    @Test
    public void constructor_invalidYearTag_throwsIllegalArgumentException() {

        // EP: zero
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.YEAR, "0"));

        // EP: non zero invalid input
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.YEAR, "7"));

        // EP: Not a number
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.YEAR, "abc"));
    }

    // EP: Invalid gender values
    @Test
    public void constructor_invalidGenderTag_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.GENDER, "invalid"));
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.GENDER, "she"));
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.GENDER, "he"));
    }

    @Test
    // EP: Invalid Major Values
    public void constructor_invalidMajorTag_throwsIllegalArgumentException() {
        // EP: empty string
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.MAJOR, ""));

        // EP: empty string with space
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.MAJOR, " "));

        // EP: Invalid character provided
        assertThrows(IllegalArgumentException.class, () -> new Tag(TagType.MAJOR, "CS@"));
    }

    @Test
    public void constructor_validYearTag_success() {
        Tag tag = new Tag(TagType.YEAR, "1");
        assertEquals("1", tag.getTagContent());
        assertEquals(TagType.YEAR, tag.getTagType());
    }

    @Test
    public void constructor_validGenderTag_success() {
        Tag tag = new Tag(TagType.GENDER, "she/her");
        assertEquals("she/her", tag.getTagContent());
        assertEquals(TagType.GENDER, tag.getTagType());
    }

    @Test
    public void constructor_validMajorTag_success() {
        Tag tag = new Tag(TagType.MAJOR, "Computer Science");
        assertEquals("Computer Science", tag.getTagContent());
        assertEquals(TagType.MAJOR, tag.getTagType());
    }

    @Test
    public void isValidTagContent_nullType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tag.isValidTagContent("test", null));
    }

    @Test
    public void isValidTagContent_nullContent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Tag.isValidTagContent(null, TagType.YEAR));
        assertThrows(NullPointerException.class, () -> Tag.isValidTagContent(null, TagType.MAJOR));
        assertThrows(NullPointerException.class, () -> Tag.isValidTagContent(null, TagType.GENDER));
    }

    @Test
    // EP: valid year values
    public void isValidTagContent_validYearTags_returnsTrue() {
        assertTrue(Tag.isValidTagContent("1", TagType.YEAR)); // boundary value
        assertTrue(Tag.isValidTagContent("2", TagType.YEAR));
        assertTrue(Tag.isValidTagContent("3", TagType.YEAR));
        assertTrue(Tag.isValidTagContent("4", TagType.YEAR));
        assertTrue(Tag.isValidTagContent("5", TagType.YEAR));
        assertTrue(Tag.isValidTagContent("6", TagType.YEAR)); // boundary value
    }

    @Test
    public void isValidTagContent_invalidYearTags_returnsFalse() {
        // EP: below range value
        assertFalse(Tag.isValidTagContent("0", TagType.YEAR));

        // EP: above range value
        assertFalse(Tag.isValidTagContent("7", TagType.YEAR));

        // EP: not a number
        assertFalse(Tag.isValidTagContent("abc", TagType.YEAR));

        // EP: empty input
        assertFalse(Tag.isValidTagContent("", TagType.YEAR));
    }

    @Test
    // EP: valid gender values
    public void isValidTagContent_validGenderTags_returnsTrue() {
        assertTrue(Tag.isValidTagContent("she/her", TagType.GENDER));
        assertTrue(Tag.isValidTagContent("he/him", TagType.GENDER));
        assertTrue(Tag.isValidTagContent("they/them", TagType.GENDER));
    }

    @Test
    public void isValidTagContent_invalidGenderTags_returnsFalse() {
        // EP: gender pronoun variants
        assertFalse(Tag.isValidTagContent("she", TagType.GENDER));
        assertFalse(Tag.isValidTagContent("he", TagType.GENDER));
        assertFalse(Tag.isValidTagContent("they", TagType.GENDER));

        // EP: not a gender pronoun
        assertFalse(Tag.isValidTagContent("invalid", TagType.GENDER));

        // EP: empty string
        assertFalse(Tag.isValidTagContent("", TagType.GENDER));
    }

    @Test
    public void isValidTagContent_validMajorTags_returnsTrue() {
        assertTrue(Tag.isValidTagContent("CS", TagType.MAJOR));
        assertTrue(Tag.isValidTagContent("Computer Science", TagType.MAJOR));
        assertTrue(Tag.isValidTagContent("Business & Economics", TagType.MAJOR));
        assertTrue(Tag.isValidTagContent("A", TagType.MAJOR));
        assertTrue(Tag.isValidTagContent("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijkl"
                + "mnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTU", TagType.MAJOR));
    }

    @Test
    public void isValidTagContent_invalidMajorTags_returnsFalse() {
        assertFalse(Tag.isValidTagContent("", TagType.MAJOR));
        assertFalse(Tag.isValidTagContent(" ", TagType.MAJOR));
        assertFalse(Tag.isValidTagContent(" CS", TagType.MAJOR));
        assertFalse(Tag.isValidTagContent("CS ", TagType.MAJOR));
        assertFalse(Tag.isValidTagContent("CS@", TagType.MAJOR));
        assertFalse(Tag.isValidTagContent("CS!", TagType.MAJOR));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Tag tag = new Tag(TagType.YEAR, "1");
        assertEquals(tag, tag);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Tag tag1 = new Tag(TagType.YEAR, "1");
        Tag tag2 = new Tag(TagType.YEAR, "1");
        assertEquals(tag1, tag2);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Tag tag1 = new Tag(TagType.YEAR, "1");
        Tag tag2 = new Tag(TagType.MAJOR, "Computer Science");
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void equals_differentContent_returnsFalse() {
        Tag tag1 = new Tag(TagType.YEAR, "1");
        Tag tag2 = new Tag(TagType.YEAR, "2");
        assertNotEquals(tag1, tag2);
    }

    @Test
    public void equals_null_returnsFalse() {
        Tag tag = new Tag(TagType.YEAR, "1");
        assertNotEquals(tag, null);
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        Tag tag = new Tag(TagType.YEAR, "1");
        assertNotEquals(tag, "string");
    }

    @Test
    public void hashCode_sameValues_returnsSameHashCode() {
        Tag tag1 = new Tag(TagType.YEAR, "1");
        Tag tag2 = new Tag(TagType.YEAR, "1");
        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void hashCode_differentValues_returnsDifferentHashCode() {
        Tag tag1 = new Tag(TagType.YEAR, "1");
        Tag tag2 = new Tag(TagType.YEAR, "2");
        assertNotEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void toString_returnsFormattedString() {
        Tag tag = new Tag(TagType.YEAR, "1");
        assertEquals("[1]", tag.toString());
    }

    @Test
    public void getTagContent_returnsCorrectContent() {
        Tag tag = new Tag(TagType.MAJOR, "Computer Science");
        assertEquals("Computer Science", tag.getTagContent());
    }

    @Test
    public void getTagType_returnsCorrectType() {
        Tag tag = new Tag(TagType.GENDER, "she/her");
        assertEquals(TagType.GENDER, tag.getTagType());
    }
}
