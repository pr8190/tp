[1mdiff --git a/docs/MoreDetailsOnFind.md b/docs/MoreDetailsOnFind.md[m
[1mindex a073f969..433a4460 100644[m
[1m--- a/docs/MoreDetailsOnFind.md[m
[1m+++ b/docs/MoreDetailsOnFind.md[m
[36m@@ -51,7 +51,7 @@[m [mirrelevant target values such as `Jon`, `AJ` or `Ben`, which would make the find[m
 | `a`      | `alex`       | Yes          | `a` is a substring of `alex`                       |[m
 | `alez`   | `alex`       | Yes          | Only 1 edit apart (substitute `z` with `x`)        |[m
 | `alexis` | `alex`       | Yes          | Only 2 edits apart (delete `i` and `s`)            |[m
[31m-| `ann`    | `ana`        | No           | Your keyword `ana` is too short for typo tolerance |[m
[32m+[m[32m| `ann`    | `ana`        | No           | Your keyword `ann` is too short for typo tolerance |[m
 [m
 ------[m
 [m
[1mdiff --git a/src/main/java/seedu/address/commons/util/StringUtil.java b/src/main/java/seedu/address/commons/util/StringUtil.java[m
[1mindex b4f26222..804d1ff8 100644[m
[1m--- a/src/main/java/seedu/address/commons/util/StringUtil.java[m
[1m+++ b/src/main/java/seedu/address/commons/util/StringUtil.java[m
[36m@@ -83,7 +83,8 @@[m [mpublic class StringUtil {[m
      * <ol>[m
      *     <li>Exact matches are always true.</li>[m
      *     <li>If the {@code query} is a substring of the {@code target}, returns true</li>[m
[31m-     *     <li>If the {@code query} are 4 characters, and its Levenshtein distance to the {@code target} is 2 or less,[m
[32m+[m[32m     *     <li>If the {@code query} are 4 characters or more, and its Levenshtein distance to the {@code target} is 2[m
[32m+[m[32m     *     or less,[m
      *     returns true (tolerating small typos).</li>[m
      * </ol>[m
      * For short strings (length 3 or less), only exact and substring matches are allowed.[m
