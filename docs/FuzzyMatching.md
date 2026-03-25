---
layout: default.md
title: "Fuzzy Matching Details"
pageNav: 3
---

# Fuzzy Matching Details

This page explains how Hall Ledger matches search keywords when you use `find`.

## 1. Name fuzzy matching (`find Alice` or `find n=Alice`)

Name matching compares each word in a resident's name against your input keywords.

For each keyword-to-name-word comparison, Hall Ledger applies these rules in order:

| Priority | Rule | What it means |
| --- | --- | --- |
| 1 | Exact | Same word, ignoring upper/lower case. |
| 2 | Contains | The keyword appears inside a name word. |
| 3 | Typo-tolerant | For words with length >= 4, up to 2 small typing mistakes are allowed. |

### Examples

- Keyword `alice` matches name words: `Alice`, `ALICE`, `malice`
- Keyword `alex` matches `alxe` (letters swapped by mistake)
- Keyword `alex` matches 'alrc' (2 typos: 'x' changed to 'r', 'e' changed to 'c')
- Keyword `abc` does **not** match `acd` (too short for typo-tolerant matching and not a substring)

## 2. Prefix-based `find` matching behavior

When using prefixed search, each field has its own matching strategy:

| Prefix | Field | Match type | Practical meaning |
| --- | --- | --- | --- |
| `n=` | Name | Fuzzy | Uses the 3 name rules above (exact, contains, typo-tolerant). |
| `p=` | Phone | Fuzzy (contains) | Input can match part of the phone value. |
| `e=` | Email | Fuzzy (contains) | Input can match part of the email value. |
| `i=` | Student ID | Fuzzy (contains) | Input can match part of the student ID. |
| `ec=` | Emergency contact | Fuzzy (contains) | Input can match part of the emergency contact value. |
| `r=` | Room number | Exact | Must match the full room value (case-insensitive). |
| `y=` | Year tag | Exact | Must match the full year tag. |
| `g=` | Gender tag | Exact | Must match the full gender tag. |
| `m=` | Major tag | Fuzzy (contains) | Input can match part of the major tag. |

## 3. AND/OR rules in prefixed `find`

- **Different prefixes** are combined with **AND**.
  - Example: `find n=Alice y=Y1` returns only residents matching both conditions.
- **Repeated same prefix** values are combined with **OR**.
  - Example: `find y=Y2 y=Y3` returns residents in Year 2 or Year 3.

## 4. Case sensitivity

All `find` matching is case-insensitive.

- `find n=alice` and `find n=ALICE` behave the same.
- `find e=GMAIL` matches `gmail.com`.

