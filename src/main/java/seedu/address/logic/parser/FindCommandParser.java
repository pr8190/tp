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
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.FilterDetails;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String MESSAGE_INVALID_GENDER_VALUES_IGNORED =
            "Warning: Ignored invalid g= value(s): %1$s. Please use he/him, she/her, or they/them.";
    private static final String MESSAGE_INVALID_YEAR_VALUES_IGNORED =
            "Warning: Ignored invalid y= value(s): %1$s. Please use year values from 1 to 6.";

    private static final Prefix[] PREFIXES_TO_CHECK = new Prefix[]{
            PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_ROOM_NUMBER, PREFIX_STUDENT_ID,
            PREFIX_EMERGENCY_CONTACT, PREFIX_TAG_YEAR, PREFIX_TAG_MAJOR, PREFIX_TAG_GENDER
    };

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand and returns a FindCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        checkForUnknownPrefixes(args);

        ArgumentMultimap argMultimap = tokenizeAndValidateArguments(args);
        FilterDetails filterDetails = buildFilterDetails(argMultimap);
        validateFilterKeywordLimits(filterDetails);

        String warningMessage = buildWarningMessage(
                collectInvalidGenderKeywords(argMultimap.getAllValues(PREFIX_TAG_GENDER)),
                collectInvalidYearKeywords(argMultimap.getAllValues(PREFIX_TAG_YEAR)));
        return new FindCommand(filterDetails, warningMessage);
    }

    private ArgumentMultimap tokenizeAndValidateArguments(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIXES_TO_CHECK)
                .removeEmptyValuesAndPrefixes();

        // Any preamble text is invalid for find because this command is prefix-only.
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // If all prefix arguments are empty, this command is also invalid.
        if (argMultimap.hasEmptyPrefixArguments()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_ARGUMENT, FindCommand.MESSAGE_USAGE));
        }
        return argMultimap;
    }

    /**
     * Builds a {@link FilterDetails} instance from the values in {@code argMultimap}.
     * All values for a given prefix are collected from {@link ArgumentMultimap#getAllValues} and converted
     * into {@code Set} to remove duplicates.
     */
    private FilterDetails buildFilterDetails(ArgumentMultimap argMultimap) {
        // Build all keyword sets from ArgumentMultimap
        Set<String> nameKeywords = toSet(argMultimap.getAllValues(PREFIX_NAME));
        Set<String> emailKeywords = toSet(argMultimap.getAllValues(PREFIX_EMAIL));
        Set<String> phoneNumberKeywords = toSet(argMultimap.getAllValues(PREFIX_PHONE));
        Set<String> roomNumberKeywords = toSet(argMultimap.getAllValues(PREFIX_ROOM_NUMBER));
        Set<String> studentIdKeywords = toSet(argMultimap.getAllValues(PREFIX_STUDENT_ID));
        Set<String> emergencyContactKeywords = toSet(argMultimap.getAllValues(PREFIX_EMERGENCY_CONTACT));
        Set<String> tagYearKeywords = toSet(argMultimap.getAllValues(PREFIX_TAG_YEAR));
        Set<String> tagMajorKeywords = toSet(argMultimap.getAllValues(PREFIX_TAG_MAJOR));
        Set<String> tagGenderKeywords = toSet(argMultimap.getAllValues(PREFIX_TAG_GENDER));

        // Normalize gender keywords
        Set<String> normalizedTagGenderKeywords = tagGenderKeywords.stream()
                .map(s -> ParserUtil.tryNormalizeGender(s).orElse(s))
                .collect(Collectors.toSet());

        // Populate FilterDetails using those sets
        FilterDetails filterDetails = new FilterDetails();
        filterDetails.setNameKeywords(nameKeywords);
        filterDetails.setEmailKeywords(emailKeywords);
        filterDetails.setPhoneNumberKeywords(phoneNumberKeywords);
        filterDetails.setRoomNumberKeywords(roomNumberKeywords);
        filterDetails.setStudentIdKeywords(studentIdKeywords);
        filterDetails.setEmergencyContactKeywords(emergencyContactKeywords);
        filterDetails.setTagYearKeywords(tagYearKeywords);
        filterDetails.setTagMajorKeywords(tagMajorKeywords);
        filterDetails.setTagGenderKeywords(normalizedTagGenderKeywords);

        return filterDetails;
    }

    /**
     *  Validates that each filter prefix has at most {@code MAX_VALUES_PER_PREFIX} values as defined in {@code
     *  FilterDetails}.
     *
     * @throws ParseException if the filter details violate the keyword limits for any prefix
     */
    private void validateFilterKeywordLimits(FilterDetails filterDetails) throws ParseException {
        try {
            filterDetails.validateKeywordLimits();
        } catch (IllegalArgumentException exception) {
            throw new ParseException(exception.getMessage());
        }
    }

    private String buildWarningMessage(List<String> invalidGenders, List<String> invalidYears) {
        List<String> warnings = new ArrayList<>();

        if (!invalidGenders.isEmpty()) {
            warnings.add(String.format(MESSAGE_INVALID_GENDER_VALUES_IGNORED, invalidGenders));
        }
        if (!invalidYears.isEmpty()) {
            warnings.add(String.format(MESSAGE_INVALID_YEAR_VALUES_IGNORED, invalidYears));
        }

        return String.join("\n", warnings);
    }

    private List<String> collectInvalidGenderKeywords(List<String> rawKeywords) {
        List<String> invalidValues = new ArrayList<>();
        for (String value : rawKeywords) {
            String trimmedValue = value.trim();
            if (ParserUtil.tryNormalizeGender(trimmedValue).isEmpty()) {
                invalidValues.add(trimmedValue);
            }
        }
        return invalidValues;
    }

    private List<String> collectInvalidYearKeywords(List<String> rawKeywords) {
        List<String> invalidValues = new ArrayList<>();
        for (String value : rawKeywords) {
            String trimmedValue = value.trim();
            if (ParserUtil.tryNormalizeYear(trimmedValue).isEmpty()) {
                invalidValues.add(trimmedValue);
            }
        }
        return invalidValues;
    }

    private static Set<String> toSet(List<String> rawKeywords) {
        return new HashSet<>(rawKeywords);
    }

    private void checkForUnknownPrefixes(String args) throws ParseException {
        String unknownPrefix = ArgumentTokenizer.checkForUnknownPrefixes(args, PREFIXES_TO_CHECK);

        if (!unknownPrefix.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_UNKNOWN_PREFIX, unknownPrefix)
                    + "\n" + FindCommand.MESSAGE_USAGE);
        }
    }
}
