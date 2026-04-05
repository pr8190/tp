package seedu.address.model.util;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.demerit.DemeritIncident;
import seedu.address.model.demerit.DemeritRule;
import seedu.address.model.demerit.DemeritRuleCatalogue;

/**
 * Utility class to help with building demerit incident lists.
 */
public class DemeritIncidentUtil {
    /**
     * Returns a demerit incident list containing the given tuples:
     * (ruleIndex, offenceNumber, remark).
     */
    public static List<DemeritIncident> getDemeritIncidentList(Object[]... incidents) {
        List<DemeritIncident> demeritIncidents = new ArrayList<>();
        for (Object[] tuple : incidents) {
            int ruleIndex = Integer.parseInt(tuple[0].toString());
            int offenceNumber = Integer.parseInt(tuple[1].toString());
            String remark = tuple.length > 2 ? tuple[2].toString() : "";

            DemeritRule rule = DemeritRuleCatalogue.findByIndex(ruleIndex)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown demerit rule index: " + ruleIndex));
            int pointsApplied = rule.getPointsForOccurrence(offenceNumber);
            demeritIncidents.add(new DemeritIncident(ruleIndex, rule.getTitle(), offenceNumber, pointsApplied, remark));
        }
        return demeritIncidents;
    }
}
