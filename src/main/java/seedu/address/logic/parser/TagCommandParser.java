package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_YEAR;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;

/**
 * Parses input arguments and creates a {@link TagCommand} object.
 *
 * <p>Requires {@code PREFIX_STUDENT_ID} and at least one tag prefix.
 * Recognised tag prefixes: {@code PREFIX_TAG_GENDER}, {@code PREFIX_TAG_MAJOR},
 * {@code PREFIX_TAG_YEAR}.
 */
public class TagCommandParser implements Parser<TagCommand> {

    private static final Prefix[] ALL_PREFIXES = {
        PREFIX_STUDENT_ID,
        PREFIX_TAG_GENDER,
        PREFIX_TAG_MAJOR,
        PREFIX_TAG_YEAR
    };

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     *                        or student ID is missing or invalid.
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ParserUtil.checkForUnknownPrefixes(args, MESSAGE_USAGE, ALL_PREFIXES);

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);
        argumentMultimap.verifyNoDuplicatePrefixesFor(ALL_PREFIXES);

        if (argumentMultimap.getValue(PREFIX_STUDENT_ID).isEmpty() || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        StudentId studentId = ParserUtil.parseStudentId(argumentMultimap.getValue(PREFIX_STUDENT_ID).get());
        Map<TagType, Tag> tags = parseTags(argumentMultimap);

        return new TagCommand(studentId, tags);
    }

    /**
     * Parses the tags from the {@code ArgumentMultimap} and returns a Map of TagType to Tag.
     *
     * @param argumentMultimap the ArgumentMultimap containing the parsed arguments.
     * @return a Map of TagType to Tag containing the parsed tags.
     * @throws ParseException if any of the tag content are invalid or if no tags are provided.
     */
    private Map<TagType, Tag> parseTags(ArgumentMultimap argumentMultimap) throws ParseException {

        Map<TagType, Tag> tags = new HashMap<>();

        try {
            putTagIfPresent(tags, argumentMultimap.getValue(PREFIX_TAG_GENDER), TagType.GENDER);
            putTagIfPresent(tags, argumentMultimap.getValue(PREFIX_TAG_MAJOR), TagType.MAJOR);
            putTagIfPresent(tags, argumentMultimap.getValue(PREFIX_TAG_YEAR), TagType.YEAR);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        if (tags.isEmpty()) {
            throw new ParseException(TagCommand.MESSAGE_TAG_NOT_ADDED);
        }

        return tags;
    }

    /**
     * Helper method to put a tag into the tags map if the value is present.
     * If the value is an empty string, it will put a null value to indicate tag removal.
     *
     * @param tags the map of tags to update
     * @param value the optional value of the tag to add or remove
     * @param type the type of the tag
     */
    private void putTagIfPresent(Map<TagType, Tag> tags, Optional<String> value, TagType type) {
        value.ifPresent(v ->
                tags.put(type, v.isEmpty()
                        ? null // sentinel to indicate tag removal if the user provided an empty string
                        : new Tag(type, tryNormalizeTagContent(v, type))));
    }

    /**
     * Tries to normalize the tag content based on the tag type using ParserUtil.
     *
     * @param content the original tag content provided by the user
     * @param type the type of the tag (GENDER, MAJOR, YEAR)
     * @return the normalized tag content if normalization is successful; otherwise, the original content
     */
    private String tryNormalizeTagContent(String content, TagType type) {
        if (type == TagType.GENDER) {
            return ParserUtil.tryNormalizeGender(content).orElse(content);
        } else if (type == TagType.YEAR) {
            return ParserUtil.tryNormalizeYear(content).orElse(content);
        } else {
            return content;
        }
    }
}
