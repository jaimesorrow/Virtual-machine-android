---
name: android-fragments
description: Fragment/View Binding component and file structure conventions in this repo (com.cloudconsole.app). Include triggers: "create a fragment", "write a screen", "build a list screen", "Android UI", "add an adapter".
---

## FILE STRUCTURE
Package root `com.cloudconsole.app`, flat by role (no per-feature packages), View Binding throughout (`buildFeatures { viewBinding true }`) — **not Compose**, despite `androidx.navigation:navigation-*-ktx` being a declared dependency (unused: there is no `nav_graph.xml` and no `NavController` anywhere):
- `fragment/` — one Fragment per drawer destination (`ComputeEngineFragment`, `CloudStorageFragment`, `DashboardFragment`, etc., 13 total), paired with `res/layout/fragment_*.xml`.
- `adapter/` — one `RecyclerView.Adapter` per list-backed model (`VMInstanceAdapter`, `StorageBucketAdapter`, etc.), paired with `res/layout/item_*.xml`.
- `activity/` — only the two non-launcher activities, `CreateVMActivity` and `VMDetailActivity`.
- `model/` — data classes + `MockDataRepository` (see the `data-model` skill).
- `MainActivity.kt` — launcher, owns the drawer and all fragment swapping.

## FRAGMENT/ADAPTER NAMING & ORGANIZATION
Fragments: `<Service>Fragment` matching the drawer label (`KubernetesFragment`, `IAMFragment`). Each holds `private var _binding: Fragment<X>Binding? = null` / `private val binding get() = _binding!!`, inflates in `onCreateView`, wires the `RecyclerView` + click listeners in `onViewCreated`, and nulls `_binding` in `onDestroyView` — follow this exact four-method shape for a new fragment. Adapters: `<Model>Adapter(private val items: List<Model>) : RecyclerView.Adapter<<Model>Adapter.<Model>ViewHolder>()`, an `inner class <X>ViewHolder(val binding: Item<X>Binding)`, view IDs prefixed by a 2-3 letter role tag (`tv_vm_name`, `tv_cluster_status`) matching the model's short name.

## STATE MANAGEMENT
One-way, no observable layer: a Fragment reads `MockDataRepository.<list>` directly in `onViewCreated` and constructs the adapter with that snapshot — no `ViewModel`, `LiveData`, or `StateFlow` sits between the repository and the UI anywhere in this repo (the `lifecycle-viewmodel-ktx`/`lifecycle-livedata-ktx` dependencies are also unused). Since the repository is immutable (see `data-model` skill), there is nothing to observe — a fragment revisited via drawer navigation just re-reads the same static list. Do not introduce a ViewModel for a single static read; only reach for one if a screen starts holding real mutable/loading state.

## NAVIGATION
Two mechanisms, don't conflate them:
1. **Drawer → Fragment**: `MainActivity.onNavigationItemSelected` is a manual `when` on `R.id.nav_*` that picks a `Fragment` + title string and calls `loadFragment()` (`supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()`, no back-stack entry). Adding a drawer item means adding both the menu XML entry and a `when` branch here.
2. **List → Detail**: plain `Intent` + `startActivity`, data passed as stringly-typed extras keyed by hand (`"vm_name"`, `"vm_zone"`, …) — see `cloudconsole-mock-review` §3 for the risk this carries. No `Bundle`/`Parcelable` model object and no Jetpack Navigation `SafeArgs` are used anywhere.

## TESTING PATTERNS
`app/src/androidTest` is empty — no Espresso or Fragment-scenario tests exist despite `espresso-core` being a declared dependency. The only test file in the repo, `app/src/test/java/com/cloudconsole/app/MockDataRepositoryTest.kt`, is a plain JUnit4 test of `MockDataRepository`'s seed data (counts, enum membership), not of any Fragment/Adapter/Activity behavior — there is no established pattern for testing UI code here to follow; a new UI test would be the first of its kind.
