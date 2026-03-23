package seedu.address.model.demerit;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Static catalogue of demerit rules used by HallLedger.
 *
 * <p>The rules and point tiers are adapted from the Demerit Point Structure
 * (DPS) for Breach of Housing Agreement issued by the Office of Student
 * Affairs, dated 9 January 2026.
 */
public final class DemeritRuleCatalogue {

    private static final List<DemeritRule> RULES = List.of(
            new DemeritRule(
                    1,
                    "Breaches involving or resulting in criminal activity / sexual misconduct / "
                            + "other serious misconduct (including vaping with etomidate or "
                            + "other drugs, sale or distribution of vapes)",
                    25, 25, 25),

            new DemeritRule(
                    2,
                    "Subletting, attempted subletting and/or accommodating non-residents, "
                            + "including overnight stay, in the room",
                    16, 16, 16),
            new DemeritRule(
                    3,
                    "Throwing of items from height ('Killer Litter')",
                    16, 16, 16),
            new DemeritRule(
                    4,
                    "Use of opposite gender’s shower and toilet facilities, including such "
                            + "inappropriate use by visitor(s) of the resident",
                    16, 16, 16),
            new DemeritRule(
                    5,
                    "Duplication of key",
                    16, 16, 16),
            new DemeritRule(
                    6,
                    "Trespassing to room or any hostel facility",
                    16, 16, 16),
            new DemeritRule(
                    7,
                    "Smoking, use, or possession of any harmful or imitation tobacco products "
                            + "prohibited by law including electronic vaporisers, shisha, "
                            + "smokeless or chewing tobacco, dissolvable or topical nicotine, "
                            + "and nasal/oral snuff such as Gutkha, Khaini, Zarda etc",
                    16, 16, 16),

            new DemeritRule(
                    8,
                    "Non-compliance with regulations, rules, policies, guidelines, codes of "
                            + "conduct or notices issued by the University or government "
                            + "authorities relating to health and safety",
                    9, 9, 9),
            new DemeritRule(
                    9,
                    "Tampering with communal security/safety measures (including fire-exits, "
                            + "cluster/lift lobby doors, toilet doors and CCTVs, false fire "
                            + "alarm)",
                    9, 9, 9),
            new DemeritRule(
                    10,
                    "Passing of hostel keys to another person",
                    9, 9, 9),
            new DemeritRule(
                    11,
                    "Leaving visitors unattended",
                    9, 9, 9),
            new DemeritRule(
                    12,
                    "Unauthorised use of or access to any hostel facility",
                    9, 9, 9),
            new DemeritRule(
                    13,
                    "Misconduct (including rude, offensive, disorderly or disruptive behaviour "
                            + "that interferes with the privacy, rights and well-being of others)",
                    9, 9, 9),
            new DemeritRule(
                    14,
                    "Disorderly conduct under the influence of alcohol",
                    9, 9, 9),
            new DemeritRule(
                    15,
                    "Smoking of cigarettes in hostel",
                    9, 9, 9),
            new DemeritRule(
                    16,
                    "Solicitation, sale or promotion of any goods or services using the hostel "
                            + "premises without permission",
                    9, 9, 9),
            new DemeritRule(
                    17,
                    "Misappropriation of common furniture, fixtures, appliances, food and/or "
                            + "personal items belonging to the University or others",
                    9, 9, 9),

            new DemeritRule(
                    18,
                    "Visit by non-residents of the hostel or visiting a resident of another "
                            + "hostel during quiet hours",
                    6, 9, 9),
            new DemeritRule(
                    19,
                    "Unauthorised swapping of rooms",
                    6, 9, 9),
            new DemeritRule(
                    20,
                    "Allowing stay-over in the room by another resident of the same hostel or "
                            + "stay-over in the room of another resident in the same hostel",
                    6, 9, 9),
            new DemeritRule(
                    21,
                    "Excessive noise during quiet hours",
                    6, 9, 9),
            new DemeritRule(
                    22,
                    "Consuming alcohol in the hostel",
                    6, 9, 9),
            new DemeritRule(
                    23,
                    "Possessing alcohol, being in the presence of alcohol consumption in the "
                            + "hostel or ordering alcohol for delivery to the hostel",
                    6, 9, 9),
            new DemeritRule(
                    24,
                    "Activities that may cause fire, explosion or any kind of hazards "
                            + "(e.g. usage of candles, open flame, 3D printers)",
                    6, 9, 9),
            new DemeritRule(
                    25,
                    "Placing of items that cause obstruction of common areas "
                            + "(e.g. passageways, stairs)",
                    6, 9, 9),
            new DemeritRule(
                    26,
                    "Possession or use of unauthorised electrical appliances, personal "
                            + "refrigerators or coolers / air conditioners without permit "
                            + "from the Management Office",
                    6, 9, 9),
            new DemeritRule(
                    27,
                    "Leaving any personal mobility device (PMD) unattended when charging "
                            + "the PMD",
                    6, 9, 9),

            new DemeritRule(
                    28,
                    "Littering and/or failure to upkeep cleanliness of common areas / room",
                    3, 6, 6),
            new DemeritRule(
                    29,
                    "Keeping of pets",
                    3, 6, 6),
            new DemeritRule(
                    30,
                    "Failure to shut or secure the fire-exit door / cluster door",
                    3, 6, 6),
            new DemeritRule(
                    31,
                    "Leaving lights / fans / appliances switched on when not in room",
                    3, 6, 6),
            new DemeritRule(
                    32,
                    "Non-compliance with prevailing safe management measures in NUS hostels",
                    3, 6, 6)
    );

    private DemeritRuleCatalogue() {}

    /**
     * Returns all demerit rules in index order.
     */
    public static List<DemeritRule> getAllRules() {
        return RULES;
    }

    /**
     * Returns the rule with the given index, if present.
     */
    public static Optional<DemeritRule> findByIndex(int index) {
        return RULES.stream()
                .filter(rule -> rule.getIndex() == index)
                .findFirst();
    }

    /**
     * Returns true if a rule with the given index exists.
     */
    public static boolean containsIndex(int index) {
        return findByIndex(index).isPresent();
    }

    /**
     * Formats all rules into a user-displayable block for the result box.
     */
    public static String formatAllRules() {
        return RULES.stream()
                .map(DemeritRule::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
