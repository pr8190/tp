---
layout: default.md
title: "Fuzzy Matching Details"
pageNav: 3
---

# Fuzzy Matching Details

This page explains how Hall Ledger matches search keywords when you use `find`.

## 1. What counts as a fuzzy match?

Hall Ledger uses fuzzy matching to make search more forgiving.

In simple terms, your keyword is treated as a match when:

1. It is the same text (ignoring uppercase/lowercase), or
2. It appears inside the target text, or
3. It is very close to the target text with a small typo (for longer words).

For short keywords (3 letters or less), Hall Ledger only uses exact/contains matching.
This prevents too many unrelated results.

Examples:

- `ali` matches `Alice`.
- `ALEX` matches `alex`.
- `sitten` can still match `kitten` (small typo).
- `ann` does **not** match `ana`.

## 2. Fields and their match types

| Prefix | Field | Match type | Practical meaning |
| --- | --- | --- | --- |
| `n=` | Name | Fuzzy | Checks each word in the name. Example: `n=alex` matches `Alex Tan`. |
| `p=` | Phone | Fuzzy | Partial phone matches are allowed. Example: `p=9123` matches `+65 91234567`. |
| `e=` | Email | Fuzzy | Partial email matches are allowed. Example: `e=@gmail` matches `alex@gmail.com`. |
| `i=` | Student ID | Fuzzy | Partial ID matches are allowed. Example: `i=1234` matches `A1234567X`. |
| `ec=` | Emergency contact | Fuzzy | Partial contact matches are allowed. |
| `r=` | Room number | Fuzzy | Partial room matches are allowed. Example: `r=12` matches `12A`. |
| `y=` | Year tag | Fuzzy | Partial year tag matches are allowed. Example: `y=1` matches `Y1`. |
| `g=` | Gender tag | Exact | Must match the full value (case-insensitive). Example: `g=male` matches `Male`. |
| `m=` | Major tag | Fuzzy | Partial major matches are allowed. Example: `m=computer sci` matches `Computer Science`. |

## 3. AND/OR rules in prefixed `find`

- **Different prefixes** are combined with **AND**.
  - Example: `find n=Alice y=Y1 m=Computer Science` returns only residents matching all conditions.
- **Repeated same prefix** values are combined with **OR**.
  - Example: `find y=Y2 y=Y3` returns residents in Year 2 or Year 3.

## 4. Case sensitivity

All `find` matching is case-insensitive.

- `find n=alice` and `find n=ALICE` behave the same.
- `find e=GMAIL` matches `gmail.com`.
