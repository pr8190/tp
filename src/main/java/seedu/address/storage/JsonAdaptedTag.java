package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagType;



/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTag {

    private static final String MESSAGE_INVALID_TAG_TYPE = "Tag must be alphanumeric. "
            + "Only gender tags may contain '/' eg: she/her or he/him or they/them"
            + "Year must be an integer between 1 to 6 (inclusive)";
    private final String tagContent;
    private final String tagType;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedTag(@JsonProperty("tagType") String tagType,
                          @JsonProperty("tagName") String tagName) {

        this.tagContent = tagName;
        this.tagType = tagType;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedTag(Tag source) {

        this.tagType = source.tagType.name();
        tagContent = source.tagContent;
    }


    public String getTagContent() {
        return tagContent;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Tag toModelType() throws IllegalValueException {
        if (tagType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, TagType.class.getSimpleName()));
        }

        TagType modelTagType;
        try {
            modelTagType = TagType.valueOf(tagType);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_INVALID_TAG_TYPE);
        }

        if (!Tag.isValidTagContent(tagContent, modelTagType)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }

        return new Tag(modelTagType, tagContent);
    }

}
