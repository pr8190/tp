package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_ARGUMENT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMERGENCY_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FilterDetails;

/**
 * Parses user input for the find command and creates a FindCommand object.
 *
 * <p><b>Parsing Flow:</b>
 * <ol>
 *   <li>Validates that input contains only known prefixes</li>
 *   <li>Tokenizes input into prefix-keyword pairs</li>
 *   <li>Builds a {@code FilterDetails}</li>
 *   <li>Validates keyword count limits per prefix</li>
 *   <li>Returns FindCommand with valid keywords and warning for invalid ones</li>
 * </ol>
 *
 * <p><b>Example:</b> Input "n=Alice g=she g=invalid y=1 y=9" produces:
 * <ul>
 *   <li>Valid keywords: name=[Alice], gender=[she/her], year=[1]</li>
 *   <li>Ignored keywords: gender=[invalid], year=[9] (shown in warning)</li>
 * </ul>
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String MESSAGE_INVALID_GENDER_KEYWORDS_IGNORED =
            "Warning: Ignored invalid g= keyword(s): %1$s. Please use he/him, she/her, or they/them.";
    private static final String MESSAGE_INVALID_YEAR_KEYWORDS_IGNORED =
            "Warning: Ignored invalid y= keyword(s): %1$s. Please use year keywords from 1 to 6.";

    private static final Prefix[] SUPPORTED_PREFIXES = new Prefix[]{
        PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ROOM_NUMBER, PREFIX_STUDENT_ID,
        PREFIX_EMERGENCY_CONTACT, PREFIX_TAG_YEAR, PREFIX_TAG_MAJOR, PREFIX_TAG_GENDER
    };

    /**
     * Parses the given arguments and creates a FindCommand object for execution.
     *
     * <p><b>Validation steps:</b>
     * <ul>
     *   <li>Rejects input with unknown prefixes </li>
     *   <li>Rejects input with invalid formats</li>
     *   <li>Rejects input exceeding max a certain number of keywords per prefix. This limit is set in
     *   {@link FilterDetails}</li>
     * </ul>
     *
     * @param args the user input string containing prefixed keywords
     * @return a FindCommand with validated keywords and optional warning message
     * @throws ParseException if input format is invalid or validation fails
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        checkForUnsupportedPrefixes(args);

        ArgumentMultimap argMultimap = tokenizeAndCheckInvalidFormat(args);

        FilterDetails filterDetails = buildFilterDetails(argMultimap);
        validateFilterKeywordLimits(filterDetails);

        // Message warns users that invalid keywords are being used for prefixes that only accepts
        // a fixed number of keywords (gender and year). Operation would still proceed with valid keywords.
        String warningMessage = buildWarningMessage(
                collectInvalidGenderKeywords(argMultimap.getAllValues(PREFIX_TAG_GENDER)),
                collectInvalidYearKeywords(argMultimap.getAllValues(PREFIX_TAG_YEAR)));

        return new FindCommand(filterDetails, warningMessage);
    }

    private void checkForUnsupportedPrefixes(String args) throws ParseException {
        String unknownPrefix = ArgumentTokenizer.checkForUnknownPrefixes(args, SUPPORTED_PREFIXES);

        if (!unknownPrefix.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_PREFIX, unknownPrefix)
                    + "\n" + FindCommand.MESSAGE_USAGE);
        }
    }

    /**
     * Tokenizes input and validates the format for the find command.
     *
     * <p><b>Validation checks:</b>
     * <ul>
     *   <li>No preamble text allowed (find is prefix-only)</li>
     *   <li>At least one prefix must have a non-empty keyword</li>
     * </ul>
     *
     * <p><b>Examples:</b>
     * <ul>
     *   <li>Valid: "find n=Alice p=91234567" -> tokenized successfully</li>
     *   <li>Invalid: "find Alice p=91234567"-> has preamble "Alice"</li>
     *   <li>Invalid: "find n= p=" -> no keywords for prefixes</li>
     * </ul>
     *
     * @param args the raw user input to tokenize and validate
     * @return ArgumentMultimap with valid prefix-keyword pairs
     * @throws ParseException if format violates constraints
     */
    private ArgumentMultimap tokenizeAndCheckInvalidFormat(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, SUPPORTED_PREFIXES)
                .removeEmptyKeywordsAndPrefixes();

        // Find command requires prefixes only, so any text before the first prefix is invalid
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // At least one prefix must have a keyword
        if (argMultimap.hasEmptyPrefixArguments()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_ARGUMENT, FindCommand.MESSAGE_USAGE));
        }
        return argMultimap;
    }

    /**
     * Builds {@code FilterDetails} from tokenized arguments.
     *
     * <p><b>Processing by prefix type:</b>
     * <ul>
     *   <li><b>Simple prefixes</b> (name, email, phone, etc.): Converted to Set, duplicates removed</li>
     *   <li><b>Prefixes that only accept a fixed number of valid keywords (gender, year):</b> Normalized, then
     *   invalid keywords are excluded from {@code FilterDetails} </li>
     * </ul>
     *
     * @param argMultimap tokenized arguments with validated format
     * @return FilterDetails containing only valid keywords
     */
    private FilterDetails buildFilterDetails(ArgumentMultimap argMultimap) {
        FilterDetails filterDetails = new FilterDetails();

        // Simple prefixes
        filterDetails.setNameKeywords(toSet(argMultimap.getAllValues(PREFIX_NAME)));
        filterDetails.setEmailKeywords(toSet(argMultimap.getAllValues(PREFIX_EMAIL)));
        filterDetails.setPhoneNumberKeywords(toSet(argMultimap.getAllValues(PREFIX_PHONE)));
        filterDetails.setRoomNumberKeywords(toSet(argMultimap.getAllValues(PREFIX_ROOM_NUMBER)));
        filterDetails.setStudentIdKeywords(toSet(argMultimap.getAllValues(PREFIX_STUDENT_ID)));
        filterDetails.setEmergencyContactKeywords(toSet(argMultimap.getAllValues(PREFIX_EMERGENCY_CONTACT)));
        filterDetails.setTagMajorKeywords(toSet(argMultimap.getAllValues(PREFIX_TAG_MAJOR)));

        // Prefixes accepting fixed number of valid keywords: normalize, then filter out invalid keywords
        Set<String> validYearKeywords = normalizeAndFilterYearKeywords(
                argMultimap.getAllValues(PREFIX_TAG_YEAR));
        filterDetails.setTagYearKeywords(validYearKeywords);

        Set<String> validGenderKeywords = normalizeAndFilterGenderKeywords(
                argMultimap.getAllValues(PREFIX_TAG_GENDER));
        filterDetails.setTagGenderKeywords(validGenderKeywords);

        return filterDetails;
    }

    /**
     /**
     * Validates that each prefix in the filter doesn't exceed the keyword limit.
     *
     * <p>This method delegates to {@link FilterDetails#validateKeywordLimits()} which is
     * responsible for checking that each prefix has at most 10 keywords.
     *
     * @param filterDetails the filter to validate
     * @throws ParseException if validation fails (converted from IllegalArgumentException)
     */
    private void validateFilterKeywordLimits(FilterDetails filterDetails) throws ParseException {
        try {
            filterDetails.validateKeywordLimits();
        } catch (IllegalArgumentException exception) {
            throw new ParseException(exception.getMessage());
        }
    }

    /**
     * Builds a user-friendly warning message for invalid keywords that would be ignored.
     *
     * <p><b>Example output:</b>
     * <pre>
     * Warning: Ignored invalid g= value(s): [invalid, xyz]. Please use he/him, she/her, or they/them.
     * Warning: Ignored invalid y= value(s): [7, 9]. Please use year values from 1 to 6.
     * </pre>
     *
     * @param invalidGenders list of invalid gender keywords (empty if all valid)
     * @param invalidYears   list of invalid year keywords (empty if all valid)
     * @return warning message string, empty if no invalid keywords
     */
    private String buildWarningMessage(List<String> invalidGenders, List<String> invalidYears) {
        List<String> warnings = new ArrayList<>();

        if (!invalidGenders.isEmpty()) {
            warnings.add(String.format(MESSAGE_INVALID_GENDER_KEYWORDS_IGNORED, invalidGenders));
        }

        if (!invalidYears.isEmpty()) {
            warnings.add(String.format(MESSAGE_INVALID_YEAR_KEYWORDS_IGNORED, invalidYears));
        }

        return String.join("\n", warnings);
    }

    /**
     * Collects gender keywords that are invalid
     */
    private List<String> collectInvalidGenderKeywords(List<String> rawKeywords) {
        List<String> invalidValues = new ArrayList<>();
        for (String value : rawKeywords) {
            String trimmedValue = value.trim();
            // If the keyword cannot be normalized, it is invalid
            if (ParserUtil.tryNormalizeGender(trimmedValue).isEmpty()) {
                invalidValues.add(trimmedValue);
            }
        }
        return invalidValues;
    }

    /**
     * Collects year keywords that are invalid
     */
    private List<String> collectInvalidYearKeywords(List<String> rawKeywords) {
        List<String> invalidValues = new ArrayList<>();
        for (String value : rawKeywords) {
            String trimmedValue = value.trim();
            // If the keyword cannot be normalized, it is invalid
            if (ParserUtil.tryNormalizeYear(trimmedValue).isEmpty()) {
                invalidValues.add(trimmedValue);
            }
        }
        return invalidValues;
    }

    /**
     * Normalizes year keywords to numeric form and excludes invalid ones.
     *
     * <p><b>Example:</b> Input ["1", "invalid", "2"] -> Output ["1", "2"]
     *
     * @param rawYearKeywords user-provided year keywords
     * @return set of valid normalized years, excludes invalid keywords entirely
     */
    private Set<String> normalizeAndFilterYearKeywords(List<String> rawYearKeywords) {
        return rawYearKeywords.stream()
                .map(String::trim)
                .map(ParserUtil::tryNormalizeYear)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private static Set<String> toSet(List<String> rawKeywords) {
        return new HashSet<>(rawKeywords);
    }

    /**
     * Normalizes gender keywords to standard forms and excludes invalid ones.
     *
     * <p><b>Example:</b> Input ["he", "invalid", "she/her"] -> Output ["he/him", "she/her"]
     *
     * @param rawGenderKeywords user-provided gender keywords
     * @return set of valid normalized genders, excludes invalid keywords entirely
     */
    private Set<String> normalizeAndFilterGenderKeywords(List<String> rawGenderKeywords) {
        return rawGenderKeywords.stream()
                .map(String::trim)
                .map(ParserUtil::tryNormalizeGender)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
