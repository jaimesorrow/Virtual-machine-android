---
name: cloudconsole-mock-review
description: Reviews diffs in this repo (Google Cloud Console for Android, package com.cloudconsole.app — despite the repo name "Virtual-machine-android" it is a fully offline, static-data mock GCP console, not a real VM/hypervisor/sandboxing app) against its actual invariants — MockDataRepository as the single, immutable, in-memory data source; toast-only "actions" that never mutate state or call any real API; stringly-typed Intent-extra handoff between adapters and detail activities; and the never-quite-consistent enum-vs-raw-String status representations across models. Use this instead of a generic code review for any change touching model/MockDataRepository.kt, any model/*.kt data class, activity/VMDetailActivity.kt or CreateVMActivity.kt, any adapter/*Adapter.kt, or any fragment/*Fragment.kt — and especially for any change that starts wiring in a real network call, credentials, or GCP SDK dependency, since that would be a genuine architecture shift this app has never had.
---

# Google Cloud Console (Android) code review

Before anything else: this repo is named "Virtual-machine-android" but the app it contains is a
**mock, offline, read-only demo UI** for a GCP-console-like experience (`com.cloudconsole.app`,
minSdk 26 / targetSdk 34, View Binding, Fragment + drawer navigation, no Hilt/DI). There is no
hypervisor, no VM execution, no sandboxing, and — as of this writing — no real network call
anywhere in `app/src/main`, despite `INTERNET` and `ACCESS_NETWORK_STATE` being declared in the
manifest. Don't invent security findings about process isolation or resource limits that don't
apply to the actual code; instead hold diffs to the invariants below, which are real and grounded
in what's here today.

## 1. MockDataRepository is the single, immutable source of truth

- `model/MockDataRepository.kt` is a Kotlin `object` whose lists (`vmInstances`, `storageBuckets`,
  `gkeClusters`, `cloudFunctions`, `bigQueryDatasets`, `cloudSQLInstances`, `iamMembers`,
  `apiServices`, `logEntries`, `projects`) are all `val ... = listOf(...)` — genuinely immutable.
  If a diff tries to make "Delete"/"Stop"/"Create" actually change what the list screens show, check
  it actually replaced these with a mutable, observable structure (e.g. `MutableList` +
  notify/LiveData/StateFlow, or promoted the repository to hold mutable state) rather than just
  reassigning a local variable in an Activity/Fragment, which will silently do nothing on
  navigating back to the list.
- Every list currently has a fixed size the existing test file hardcodes (see §5). Any edit to the
  seed data in `MockDataRepository` needs a matching test update, not just a silent count drift.

## 2. "Action" buttons are intentionally toast-only — don't let that quietly change

- `VMDetailActivity.setupButtons()` (start/stop, SSH, delete) and `CreateVMActivity.setupButtons()`
  (create) each only show a `Toast` and, for delete/cancel/create, call `finish()`. None of them
  mutate `MockDataRepository`, so leaving `VMDetailActivity` after "deleting" a VM still shows it in
  `ComputeEngineFragment`'s list. This is consistent today — flag it as a **new** bug only if a diff
  makes some actions persist (e.g. wires delete through to state per §1) while leaving sibling
  actions (stop/start) still toast-only with no comment, since that's an inconsistent half-migration
  a reviewer should call out explicitly.
- Any new "action" button added to a detail/list screen should follow the existing pattern
  (`Toast.makeText(this, "<Verb>ing $name...", Toast.LENGTH_SHORT).show()`) unless the PR explicitly
  says it's the one making actions real — a button that silently calls through to a real GCP API,
  reads real credentials, or performs a real destructive operation would be a major, undiscussed
  scope change for this app and should be flagged as such, not nodded through as a UI tweak.

## 3. Detail-screen data flows through stringly-typed Intent extras

- `VMInstanceAdapter.onBindViewHolder` packs `VMInstance` fields into `Intent` extras with keys like
  `"vm_name"`, `"vm_zone"`, `"vm_machine_type"`, `"vm_status"`, `"vm_internal_ip"`,
  `"vm_external_ip"`, `"vm_os"`, `"vm_disk_gb"`, `"vm_created"`; `VMDetailActivity.populateDetails()`
  reads them back by the same string keys with a fallback default (`"Unknown"`, `"N/A"`, etc.) if a
  key is missing or misspelled. There is no compiler check tying the two sides together. For any
  diff touching either side: verify every extra the adapter puts has a matching, correctly-spelled
  `getStringExtra`/`getIntExtra` read, and that a newly added field on `VMInstance` (or any other
  model wired the same way) has both a put and a get — a typo'd key fails silently into the default
  string rather than a crash, so it won't show up in a quick manual test unless someone specifically
  checks that field's value on the detail screen.
- `VMDetailActivity` re-derives status display by matching on the **raw string** `"RUNNING"` /
  `"STOPPED"` / `"TERMINATED"` (falling through to a generic `else` for `PROVISIONING`, `STAGING`,
  `STOPPING`, `SUSPENDED` — all valid `VMStatus` enum values `VMInstanceAdapter` itself handles
  explicitly). If a diff adds handling for one of those missing states in the adapter's list view,
  check whether the same state needs equivalent handling in `VMDetailActivity`'s `when` — right now
  the two are already out of sync and a new state added to only one of them widens that gap.

## 4. Status representation is inconsistent across models — don't add a third convention

- `VMInstance.status` is the enum `VMStatus` (`RUNNING, STOPPED, PROVISIONING, STAGING, STOPPING,
  TERMINATED, SUSPENDED`), compiler-checked in `VMInstanceAdapter`.
- `GKECluster.status`, `CloudFunction.status`, `CloudSQLInstance.status` are plain `String` with no
  enum, matched by ad-hoc `when` branches per adapter (`CloudSQLAdapter` matches `"RUNNABLE"` /
  `"SUSPENDED"`; `CloudFunctionAdapter` matches `"ACTIVE"` / `"FAILED"`), each with its own `else ->`
  fallback color and no shared vocabulary of valid values.
- For any new model or adapter with a status-like field, match whichever convention its closest
  sibling already uses rather than inventing a third status representation — and if a PR converts
  one of the raw-`String` fields to an enum, check it updates every `MockDataRepository` seed value,
  every adapter `when`, and (per §3) any Activity that re-parses that status from an Intent extra.

## 5. Test coverage (`app/src/test/java/com/cloudconsole/app/MockDataRepositoryTest.kt`)

- This is the only test file in the repo. Several assertions are hardcoded counts (`"Should have 5
  VMs"`, `"Should have 5 buckets"`, `"Should have 3 projects"`). A diff that changes
  `MockDataRepository`'s seed data without touching these will leave the suite red — flag a data
  change with no corresponding test update.
- A diff that adds a new list to `MockDataRepository` (following the existing pattern) with no
  analogous test is a coverage gap worth calling out, even though nothing currently enforces it.

## 6. Manifest / component exposure

- Only `MainActivity` is `android:exported="true"` (it's the launcher); `CreateVMActivity` and
  `VMDetailActivity` are correctly `exported="false"`. Any new `Activity` should default to
  `exported="false"` unless it genuinely needs to be launched from outside the app — flag an
  unexplained `exported="true"` on a new component.
- `INTERNET` / `ACCESS_NETWORK_STATE` are declared but unused by any real network call today. If a
  diff adds real network I/O, check it doesn't silently expand the permission set further (broad
  storage/location/etc.) beyond what that specific feature needs.

## Output

Report findings most-severe first, each with file:line, a one-sentence description of what's wrong,
and a concrete scenario where it bites (e.g. "adding SUSPENDED handling only in the adapter means
the detail screen for a suspended VM still shows a generic `else` bullet"). A change that
half-migrates this app from mock-toast-only actions toward real mutation or real network calls
(§2) is the one class of finding worth flagging even above ordinary correctness bugs, since it
changes what kind of app this is without anyone deciding that on purpose.
