package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }

    //---------------- Tests for equalsAnyIgnoreCase --------------------------------------

    @Test
    public void equalsAnyIgnoreCase_nullOrEmptyInputs_throwsException() {
        // Null inputs
        Assertions.assertThrows(NullPointerException.class, () ->
                StringUtil.equalsAnyIgnoreCase(null, Set.of("abc")));
        Assertions.assertThrows(NullPointerException.class, () ->
                StringUtil.equalsAnyIgnoreCase("abc", null));

        // Empty inputs
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                StringUtil.equalsAnyIgnoreCase("", Set.of("abc")));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                StringUtil.equalsAnyIgnoreCase("abc", Collections.emptySet()));
    }

    @Test
    public void equalsAnyIgnoreCase_validInputs_correctResult() {
        Set<String> wordSet = Set.of("Alice", "Bob", "Charlie");

        // Exact match
        assertTrue(StringUtil.equalsAnyIgnoreCase("Alice", wordSet));

        // Case-insensitive match
        assertTrue(StringUtil.equalsAnyIgnoreCase("alice", wordSet));
        assertTrue(StringUtil.equalsAnyIgnoreCase("BOB", wordSet));

        // Whitespace trimmed match
        assertTrue(StringUtil.equalsAnyIgnoreCase("\n  Charlie  \t", wordSet));

        // No match
        assertFalse(StringUtil.equalsAnyIgnoreCase("Dave", wordSet));
        assertFalse(StringUtil.equalsAnyIgnoreCase("Alic", wordSet));
    }

    //---------------- Tests for fuzzyMatchesIgnoresCase --------------------------------------

    @Test
    public void fuzzyMatchesIgnoresCase_nullOrEmptyInputs_throwsException() {
        // Null inputs
        Assertions.assertThrows(NullPointerException.class, () -> StringUtil.fuzzyMatchesIgnoresCase(null, "abc"));
        Assertions.assertThrows(NullPointerException.class, () -> StringUtil.fuzzyMatchesIgnoresCase("abc", null));

        // Empty inputs
        Assertions.assertThrows(IllegalArgumentException.class, () -> StringUtil.fuzzyMatchesIgnoresCase("", "abc"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> StringUtil.fuzzyMatchesIgnoresCase("abc", ""));
    }

    @Test
    public void fuzzyMatchesIgnoresCase_exactMatches_returnsTrue() {
        // Exact match
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("abc", "abc"));

        // Exact match, case-insensitive
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("abc", "ABC"));
    }

    @Test
    public void fuzzyMatchesIgnoresCase_substringMatches_returnsTrue() {
        // query is substring of target
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("bcd", "abcde"));

        // query is substring of target - case-insensitive
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("BcD", "abcde"));
    }

    @Test
    public void fuzzyMatchesIgnoresCase_shortStrings_allowOnlyExactOrSubstring() {
        // Short queries can still match via substring.
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("ab", "abc"));
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("ab", "zabcd"));

        // Short strings without exact/substring match should fail.
        assertFalse(StringUtil.fuzzyMatchesIgnoresCase("ab", "ac"));
        assertFalse(StringUtil.fuzzyMatchesIgnoresCase("ab", "xay"));

        // both target and query are too short for typo-distance matching
        assertFalse(StringUtil.fuzzyMatchesIgnoresCase("a", "eb"));
        assertFalse(StringUtil.fuzzyMatchesIgnoresCase("a", "bd"));
    }

    @Test
    public void fuzzyMatchesIgnoresCase_withinDistance_returnsTrue() {
        // Distance 1
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("kitten", "sitten"));
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("kitten", "kittn"));

        // Distance 2
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("kitten", "kitti"));
        assertTrue(StringUtil.fuzzyMatchesIgnoresCase("example", "exmpl"));
    }

    @Test
    public void fuzzyMatchesIgnoresCase_exceedDistance_returnsFalse() {
        // Distance > 2
        assertFalse(StringUtil.fuzzyMatchesIgnoresCase("kitten", "kitchren"));
        assertFalse(StringUtil.fuzzyMatchesIgnoresCase("hello", "olleh"));

        // Not substring and distance > 2
        assertFalse(StringUtil.fuzzyMatchesIgnoresCase("abc", "xyz"));
    }

    //---------------- Tests for fuzzyMatchesAnyIgnoreCase --------------------------------------

    @Test
    public void fuzzyMatchesAnyIgnoreCase_validInputs_correctResult() {
        Set<String> wordSet = Set.of("kitten", "puppy", "Constitution");

        // Exact match
        assertTrue(StringUtil.fuzzyMatchesAnyIgnoreCase("kitten", wordSet));

        // Fuzzy match (1 edit)
        assertTrue(StringUtil.fuzzyMatchesAnyIgnoreCase("sitten", wordSet));

        // Fuzzy match (2 edits)
        assertTrue(StringUtil.fuzzyMatchesAnyIgnoreCase("kitti", wordSet));

        // Substring match
        assertTrue(StringUtil.fuzzyMatchesAnyIgnoreCase("unconstitutional", wordSet));

        // No match (too different and not substring)
        assertFalse(StringUtil.fuzzyMatchesAnyIgnoreCase("dragon", wordSet));

        // Distance match with different case
        assertTrue(StringUtil.fuzzyMatchesAnyIgnoreCase("kiTTens", wordSet));

        // Query words come from wordSet; no reverse-substring match
        assertFalse(StringUtil.fuzzyMatchesAnyIgnoreCase("abc", Set.of("abcxyz")));
        assertTrue(StringUtil.fuzzyMatchesAnyIgnoreCase("abcxyz", Set.of("abc")));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }
}
