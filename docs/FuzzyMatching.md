---
layout: default.md
title: "Fuzzy Matching Details"
pageNav: 3
---

# Fuzzy Matching Details

This page explains how Hall Ledger decides whether a resident matches your `find` input.

## 1. Quick idea

<box type="info" seamless>

Hall Ledger uses fuzzy matching to make search more forgiving.

In simple terms, your keyword is treated as a match when:

1. It is the same text (ignoring uppercase/lowercase), or
2. It appears inside the target text, or
3. It is very close to the target text with a small typo (for longer words).

For short keywords (3 letters or less), Hall Ledger only uses rule 1: exact matching, and rule 2: substring
matching.
This prevents too many unrelated results.

Examples:

- `ALEX` matches `alex`.
- `ali` matches `Alice`. (`ali` is a substring of `Alice`.)
- `sitten` can still match `kitten` (small typo).
- `ann` does **not** match `ana` (the keywords are too short, so the typo is not forgiven).
</box>

Examples:

| You type | Resident value | Match? | Why |
| --- | --- | --- | --- |
| `ali` | `Alice` | Yes | `ali` is contained in `Alice`. |
| `ALEX` | `alex` | Yes | Matching ignores letter case. |
| `sitten` | `kitten` | Yes | Small typo, still considered close. |
| `ann` | `ana` | No | Not close enough for a short input. |

## 2. Which fields use which matching style?

| Prefix | Field             | Matching style           | Example                                                                               |
|--------|-------------------|--------------------------|---------------------------------------------------------------------------------------|
| `n=`   | Name              | Fuzzy                    | `n=alex` can match `Alex Tan`.                                                        |
| `p=`   | Phone             | Fuzzy                    | `p=9123` can match `+65 91234567`.                                                    |
| `e=`   | Email             | Fuzzy                    | `e=@gmail` can match `alex@gmail.com`.                                                |
| `i=`   | Student ID        | Exact (case-insensitive) | `i=A1234567X` matches `A1234567X`; `i=1234` does not match `A1234567X`.               |
| `ec=`  | Emergency contact | Fuzzy                    | `ec=9876` can match `+65 98765432`.                                                   |
| `r=`   | Room number       | Fuzzy                    | `r=12` can match `12A`.                                                               |
| `y=`   | Year tag          | Fuzzy                    | `y=1` can match `Y1`.                                                                 |
| `m=`   | Major tag         | Fuzzy                    | `m=computer sci` can match `Computer Science`.                                        |
| `g=`   | Gender tag        | Special                  | `g=he` matches `he/him`; `g=her` matches `she/her`, `g=they/them` matches `they/them` |

## 3. How multiple filters combine

<box type="info" seamless>

- Using different prefixes requires filter results that matches all fields.
  - `find n=Alice y=Y1` means: name matches `Alice` **and** year matches `Y1`.
  - `find ec=alice@gmail.com m=CS` means: email matches `alice@gmail.com` **and** major matches `CS`.
- Repeating one prefix widens that single filter.
  - `find y=Y2 y=Y3` means: year is `Y2` **or** `Y3`.
  - `find n=Alice n=Bob` means: name matches `Alice` **or** `Bob`.
  - `find n=Alice n=Bob y=Y2 y=Y3` means: (name matches `Alice` **or** `Bob`) **and** (year is `Y2` **or** `Y3`).

</box>

## 4. Case sensitivity

All `find` matching is case-insensitive.

- `find n=alice` and `find n=ALICE` behave the same.
- `find e=GMAIL` can match `gmail.com`.
