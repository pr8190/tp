package seedu.address.ui.filter;

import java.util.List;

/**
 * Handler for when keywords in a filter input are edited.
 * Validates and processes keyword changes in filter inputs.
 */
@FunctionalInterface
public interface KeywordsChangedHandler {
    /**
     * Handles a change in keywords and returns the validated list of keywords.
     *
     * @param keywords the proposed list of keywords
     * @return the validated list of keywords to be displayed
     */
    List<String> handle(List<String> keywords);
}

