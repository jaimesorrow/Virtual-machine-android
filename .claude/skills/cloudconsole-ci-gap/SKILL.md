---
name: cloudconsole-ci-gap
description: Flags that this repo (Virtual-machine-android / com.cloudconsole.app) has no CI workflow of any kind — no .github/workflows, no other CI config — so app/src/test/java/com/cloudconsole/app/MockDataRepositoryTest.kt, the repo's only test file, is never run automatically; it only executes when someone manually invokes `./gradlew test`. Use this when a diff adds or changes a GitHub Actions workflow (or any other CI config) in this repo, when a PR description or commit message claims "tests pass" or "CI is green" as evidence for this repo, or when asked to review this repo's test/CI setup — so that claim isn't taken at face value without checking whether anything actually ran the suite.
---

# CI gap: no automated enforcement of MockDataRepositoryTest.kt

As of this writing there is **no CI configuration anywhere in this repository** — no
`.github/workflows/*.yml`, no CircleCI, Travis, Jenkins, or Azure Pipelines config, nothing. The
only test file, `app/src/test/java/com/cloudconsole/app/MockDataRepositoryTest.kt`, is real and
passes when run, but nothing triggers it on push or PR. It is effectively dead weight from an
enforcement standpoint: a change to `MockDataRepository.kt`'s seed data (or anything else the test
covers, per the `cloudconsole-mock-review` skill's §5) can land with a red local test suite and nothing
in GitHub will show it.

## What to check

- If a diff adds a `.github/workflows/*.yml` (or other CI config) to this repo: verify it actually
  invokes a test task — `./gradlew test` or `./gradlew testDebugUnitTest` — on a trigger that fires
  for normal pushes/PRs (not just `workflow_dispatch`), and that it isn't scoped to a path filter
  that would exclude `app/src/test/**` or `MockDataRepository.kt` itself. A workflow that only runs
  `assembleDebug`/`lint` without a `test` step would give the appearance of CI coverage while still
  leaving `MockDataRepositoryTest.kt` unenforced — flag that explicitly rather than assuming "there's
  a workflow file now" means the test runs.
- If a PR description, commit message, or reviewer comment asserts "tests pass" or "CI passed" as
  justification for a change in this repo, don't take it as verified — no CI job produces that
  signal today. The only way tests ran is if someone ran `./gradlew test` locally and reported the
  result themselves.
- This is a coverage-process gap, not a correctness bug in app code — don't conflate it with the
  invariants in `cloudconsole-mock-review` (which cover `MockDataRepository`'s data/behavior itself).
  Raise it separately, and only when a diff or claim actually touches CI setup or test-passing claims
  as described above.
