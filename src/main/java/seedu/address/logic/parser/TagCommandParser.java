package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;

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
 * <p>Expects a student ID prefix and at least one tag prefix. Recognised prefixes are:
 * <ul>
 *   <li>{@code PREFIX_STUDENT_ID} — identifies the target resident</li>
 *   <li>{@code PREFIX_TAG_GENDER} — gender pronouns tag</li>
 *   <li>{@code PREFIX_TAG_MAJOR} — academic major tag</li>
 *   <li>{@code PREFIX_TAG_YEAR} — year of study tag</li>
 * </ul>
 */
public class TagCommandParser implements Parser<TagCommand> {

    private static final Prefix[] ALL_PREFIXES = {
        CliSyntax.PREFIX_STUDENT_ID,
        CliSyntax.PREFIX_TAG_GENDER,
        CliSyntax.PREFIX_TAG_MAJOR,
        CliSyntax.PREFIX_TAG_YEAR
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
            putTagIfPresent(tags, argumentMultimap.getValue(CliSyntax.PREFIX_TAG_GENDER), TagType.GENDER);
            putTagIfPresent(tags, argumentMultimap.getValue(CliSyntax.PREFIX_TAG_MAJOR), TagType.MAJOR);
            putTagIfPresent(tags, argumentMultimap.getValue(CliSyntax.PREFIX_TAG_YEAR), TagType.YEAR);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }

        if (tags.isEmpty()) {
            throw new ParseException(TagCommand.MESSAGE_TAG_NOT_ADDED);
        }

        return tags;
    }

    private void putTagIfPresent(Map<TagType, Tag> tags, Optional<String> value, TagType type) {
        value.ifPresent(v ->
                tags.put(type, v.isEmpty()
                        ? null // sentinel to indicate tag removal if the user provided an empty string
                        : new Tag(type, tryNormalizeTagContent(v, type))));
    }

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
