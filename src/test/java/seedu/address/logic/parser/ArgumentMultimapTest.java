package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class ArgumentMultimapTest {

    private final Prefix preamblePrefix = new Prefix("");
    private final Prefix emptyPrefix = new Prefix("\n \t \n");

    @Test
    public void put_newPrefix_addsValue() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "value1");

        assertEquals(Optional.of("value1"), map.getValue(PREFIX_NAME));
        assertEquals(List.of("value1"), map.getAllValues(PREFIX_NAME));
    }

    @Test
    public void put_existingPrefix_appendsValue() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "value1");
        map.put(PREFIX_NAME, "value2");

        assertEquals(Optional.of("value2"), map.getValue(PREFIX_NAME));
        assertEquals(List.of("value1", "value2"), map.getAllValues(PREFIX_NAME));
    }

    @Test
    public void getValue_nonExistentPrefix_returnsEmpty() {
        ArgumentMultimap map = new ArgumentMultimap();
        assertEquals(Optional.empty(), map.getValue(PREFIX_NAME));
    }

    @Test
    public void getAllValues_nonExistentPrefix_returnsEmptyList() {
        ArgumentMultimap map = new ArgumentMultimap();
        assertTrue(map.getAllValues(PREFIX_NAME).isEmpty());
    }

    @Test
    public void getPreamble_present_returnsPreamble() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(preamblePrefix, "preamble text");
        assertEquals("preamble text", map.getPreamble());
    }

    @Test
    public void getPreamble_absent_returnsEmptyString() {
        ArgumentMultimap map = new ArgumentMultimap();
        assertEquals("", map.getPreamble());
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_noDuplicates_success() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "value1");
        map.put(PREFIX_PHONE, "value2");

        assertDoesNotThrow(() -> map.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE));
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_duplicates_throwsParseException() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "value1");
        map.put(PREFIX_NAME, "value2");

        assertThrows(ParseException.class, () -> map.verifyNoDuplicatePrefixesFor(PREFIX_NAME));
    }

    @Test
    public void removeEmptyValuesAndPrefixes_removesEmptyStrings() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, " ");
        map.put(PREFIX_PHONE, "value");
        map.put(PREFIX_PHONE, "");

        ArgumentMultimap cleanedMap = map.removeEmptyValuesAndPrefixes();

        assertTrue(cleanedMap.getAllValues(PREFIX_NAME).isEmpty()); // Prefijo eliminado completamente
        assertEquals(List.of("value"), cleanedMap.getAllValues(PREFIX_PHONE)); // Solo queda el no vacío
    }

    @Test
    public void hasEmptyPrefixArguments_allEmpty_returnsTrue() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, " ");
        map.put(PREFIX_PHONE, "");
        map.put(emptyPrefix, " \t \n ");
        assertTrue(map.hasEmptyPrefixArguments());
    }

    @Test
    public void hasEmptyPrefixArguments_hasValues_returnsFalse() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "value");
        assertFalse(map.hasEmptyPrefixArguments());
    }

    @Test
    public void hasEmptyPrefixArguments_mixedEmptyAndNonEmpty_returnsFalse() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "");
        map.put(PREFIX_PHONE, "value");
        assertFalse(map.hasEmptyPrefixArguments());
    }

    @Test
    public void getNonEmptyValuesCount_allPrefixesHaveOneNonEmptyValue_returnsOneForEachPrefix() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, "Alice");
        map.put(PREFIX_PHONE, "+65 91234567");

        List<Long> countByPrefix = map.getNonEmptyValuesCount(PREFIX_NAME, PREFIX_PHONE);

        assertEquals(List.of(1L, 1L), countByPrefix);
    }

    @Test
    public void getNonEmptyValuesCount_mixedEmptyAndNonEmptyValues_countsOnlyNonEmptyValues() {
        ArgumentMultimap map = new ArgumentMultimap();
        map.put(PREFIX_NAME, " ");
        map.put(PREFIX_NAME, "Alice");
        map.put(PREFIX_NAME, "\t");
        map.put(PREFIX_PHONE, "");
        map.put(PREFIX_PHONE, "+65 91234567");

        List<Long> countByPrefix = map.getNonEmptyValuesCount(PREFIX_NAME, PREFIX_PHONE);

        assertEquals(List.of(1L, 1L), countByPrefix);
    }

    @Test
    public void getNonEmptyValuesCount_missingPrefixAndNoPrefixesProvided_returnsZeroAndEmptyList() {
        ArgumentMultimap map = new ArgumentMultimap();
        Prefix missingPrefix = new Prefix("e=");

        List<Long> missingPrefixCount = map.getNonEmptyValuesCount(missingPrefix);
        List<Long> noPrefixCount = map.getNonEmptyValuesCount();

        assertEquals(List.of(0L), missingPrefixCount);
        assertEquals(List.of(), noPrefixCount);
    }
}
