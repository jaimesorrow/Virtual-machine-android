---
name: pr-writer
description: Draft pull request descriptions matching this repo's conventions. Include triggers: "write a PR description", "create a PR title", "describe this change", "summarize this PR".
---

## HISTORY IS THIN — be honest about it
This repo has exactly one merged PR (#1, "Build Google Cloud Console Android app", the original
Copilot-authored scaffold) and one open, unmerged bot PR (#2, a CircleCI config from the
`circleci-app` bot). There is no history of a human or Claude writing and merging a PR here yet.
Treat the conventions below as extracted from that single real data point, not as a large established
pattern — don't overstate confidence in "this repo always does X."

## TITLE FORMAT
Plain imperative sentence, capitalized, no ticket number, no scope prefix: "Build Google Cloud
Console Android app" (#1). #2's title ("Add optimized CircleCI build pipeline for Android") follows
the same shape. Keep new titles this way — imperative verb first, no `feat:`/`fix:` prefix seen
anywhere in this repo's history.

## DESCRIPTION SECTIONS
#1 (the one substantive PR) uses `## Architecture` (stack/libraries in a short bullet list) followed
by a per-feature bullet list (`## Screens & Adapters`, one bold-lead bullet per screen naming the
concrete status values / behaviors it shows) and a `## Data Models` line listing the exact data
class names touched. #2 uses `## Summary` (bullets, one change per line) plus a markdown table for
itemized optimizations/tradeoffs. For a new PR: use `## Summary` for what changed, name the actual
files/classes/fragments touched (per this repo's `data-model` and `android-fragments` skills) rather
than describing screens abstractly, and add a `## Test plan` section — neither existing PR has one,
but this repo has zero CI (`cloudconsole-ci-gap`) and only one test file, so a PR touching
`MockDataRepository` or any fragment/adapter should say explicitly whether `./gradlew test` was run
and what it covered, rather than let "tests pass" go unstated or implied.

## DETAIL LEVEL
Name concrete classes and files, not vague feature descriptions — #1 names `MockDataRepository`,
`VMInstance`, `StorageBucket`, etc. directly rather than saying "added data models." If a change
touches a toast-only action, a stringly-typed Intent extra, or an enum-vs-String status field (the
three live invariants in `cloudconsole-mock-review`), say so explicitly rather than letting it pass
as an implementation detail — a reviewer checking this repo's known trouble spots should be able to
tell from the description alone whether the PR touches one.

## TONE & AUDIENCE
Technical and direct. Both existing PRs are agent-authored (Copilot, CircleCI bot) for a
single-maintainer repo — there's no release-notes or external-user audience to write for.

## CHECKLIST
No recurring pre-merge checklist exists yet in this repo's history (no `## Test plan` in either PR).
Introduce one on new PRs: a `- [ ]`/`- [x]` list naming the exact command run (`./gradlew test`,
`./gradlew lint`) and its outcome, or the blocking reason if it couldn't run (e.g. no Android SDK in
the sandbox) — don't claim untested work passed.
