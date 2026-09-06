---
name: user-facing-mock-honesty
description: Reviews this repo's actual user-visible UI copy (strings.xml, layout XML text, and any hardcoded fragment/activity text) for whether a real user could mistake com.cloudconsole.app for a genuine, connected GCP console — a different angle from cloudconsole-mock-review, which covers internal code consistency (MockDataRepository, toast-only actions, Intent-extra plumbing), not what the screen actually tells a viewer. Use this for any change to res/values/strings.xml, any res/layout/*.xml text/hint/label, app_name or nav_header_main.xml, or any hardcoded `.text =` string in a fragment/activity (billing figures, cost estimates, account/project placeholders) — and especially before ever adding a Play Store listing, screenshots, or any distribution-facing description for this app.
---

# User-facing mock honesty

This app's only real product promise (see repo CLAUDE.md-equivalent framing: it's `com.cloudconsole.app`,
a fully offline mock GCP console despite the repo name "Virtual-machine-android") is that it stays
honestly, visibly a demo. `cloudconsole-mock-review` already covers whether the *code* stays internally
consistent as a mock (immutable `MockDataRepository`, toast-only actions, stringly-typed Intent
extras). This skill covers a different failure mode: **whether the screen itself ever tells the person
looking at it that none of this is real.**

## The gap, as it stands today

Grep the shipped UI for any disclosure and there is none:

```
grep -rniE "mock|demo|simulat|fake|not real|sample data|offline" app/src/main/res app/src/main/java
```

The only hits are the identifier `MockDataRepository` in Kotlin source — never a user-visible string.
Concretely, a person holding this app sees:

- **App identity**: `android:label="@string/app_name"` → `"Cloud Console"` (`res/values/strings.xml:2`,
  `AndroidManifest.xml`). Not "Cloud Console Demo," not "Mock Console" — just the generic product name.
- **Drawer header** (`res/layout/nav_header_main.xml`): `"☁ Google Cloud Console"` as the title, with a
  fabricated-but-plausible project id `"my-project-123"` and account `"admin@mycompany.com"` sitting
  right where a real GCP console shows the signed-in identity and project — no "DEMO" ribbon, no
  differently-colored border, nothing.
- **Billing screen** (`BillingFragment.populateBilling()`, backing `res/layout/fragment_billing.xml`):
  hardcoded, invoice-realistic figures — `"$2,847.32"` this month, `"$2,612.45"` last month,
  `"$3,200.00"` forecasted, `"81% of budget used"`, a per-service cost breakdown, credits applied, net
  cost — laid out exactly like a real GCP billing summary card, with no "sample data" caption anywhere
  on the card.
- **Create VM screen** (`res/layout/activity_create_vm.xml`, `CreateVMActivity`): real GCP region names
  (`us-central1-a`, `europe-west1-b`, …) and real machine-type strings
  (`n2-standard-4 (4 vCPUs, 16 GB RAM)`) in the dropdowns, a line reading `"Estimated monthly cost"` /
  `"$24.27/month"`, and a `"Pricing Calculator"` link-style row — all styled as authoritative GCP
  pricing, not placeholder data. Tapping the `"Create"` button shows a toast (`"Creating VM instance
  '$name'..."`) and closes the screen; nothing in the screen itself signals in advance that the button
  does not talk to GCP.
- **VM detail screen** (`res/layout/activity_vm_detail.xml`, `VMDetailActivity`): a `"SSH COMMAND"` card
  showing a real, copy-pasteable-looking `gcloud compute ssh <name> --zone=<zone>` invocation, plus
  `"SSH in Browser"`, `"Stop Instance"`, and `"Delete Instance"` buttons with no visual "demo action"
  affordance — each just fires a toast.

None of this is hidden from a developer reading the code (`MockDataRepository`'s name, and README.md's
"Data: Mock repository with realistic GCP sample data" line, are honest) — but README.md is not part of
the shipped APK. Everything a person actually sees after installing and opening the app is
indistinguishable, by content and layout, from a genuine connected console showing their real project,
their real bill, and real destructive controls over compute they are paying for.

## What to check on a diff

- **Any new or edited string in `res/values/strings.xml` or a layout's `android:text`/`android:hint`**:
  does it read as GCP's own copy (a real product name, a real gcloud command, a realistic dollar
  figure, a plausible account/project identifier) with no accompanying mock/demo signal in the same
  view? Flag it if so — not because the underlying data is wrong (it's supposed to look like GCP data),
  but because there is still nowhere on screen that says "this is a demo."
- **Any hardcoded `.text = "$...".` money value added to a fragment/activity** (billing, cost estimates,
  invoices): the risk compounds specifically for money-shaped strings — a user glancing at a phone
  screen showing "$2,847.32" and "81% of budget used" has no in-app cue distinguishing that from a real
  linked billing account. Treat a new realistic-looking cost figure as a place this finding gets worse,
  not better.
- **`app_name`, the launcher label, and `nav_header_main.xml`'s title/subtitle/account lines**: these
  are the first and most persistent things a user sees (launcher icon list, drawer every time it's
  opened) — the highest-leverage single place a "Demo" mark could go, and currently the place it's most
  conspicuously absent.
- **Any button that performs a destructive-sounding action** (`Delete Instance`, `Stop Instance`) or
  implies real cost (`Create`, pricing estimates): if a PR adds a new one, check whether the PR also
  adds any accompanying visual/textual cue that the action is inert — if not, that's consistent with
  today's pattern (which itself is the gap), so call it out rather than silently approving another
  instance of it.
- **Do not conflate this with `cloudconsole-mock-review`**: that skill's concern is whether the mock
  stays *internally* coherent (does delete actually persist, does the adapter/detail-screen status
  parsing agree). This skill's concern is orthogonal and can fire even when the mock is perfectly
  internally consistent — the question here is only ever "would a real user looking at this screen
  know it's fake," never "does the toast fire correctly."
- **Any Play Store listing, app description, screenshot set, or release-facing copy** added to this
  repo (there is none today): this is the single highest-severity version of this gap — a listing that
  reuses this app's real-looking screenshots without prominent "demo"/"mock" language in the listing
  itself would let someone install it while genuinely believing it manages real GCP resources or bills
  them for real spend. Flag any such addition as a launch blocker, not a style nit.

## Output

State plainly that as of this writing there is a total absence of user-facing mock/demo disclosure
anywhere in the shipped UI (app name, drawer header, billing figures, create/delete flows) — this is
the app's actual product-honesty gap, distinct from and complementary to `cloudconsole-mock-review`'s
internal-consistency findings. For a diff, name the specific file:line of any new/changed user-facing
string and say concretely how a real user could misread it (e.g. "a user who genuinely has a GCP
project named similarly to the placeholder could mistake the Billing tab's $2,847.32 for their real
bill"). Don't demand every screen carry a giant banner if the PR's scope doesn't call for it — but do
flag when a change makes the illusion more convincing (a more realistic dollar figure, a more
plausible account email, a real-looking new destructive button) with no offsetting disclosure added
anywhere in the app.
