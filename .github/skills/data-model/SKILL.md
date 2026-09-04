---
name: data-model
description: A reference for the mock GCP data schema in this repo (com.cloudconsole.app) — entity fields, how MockDataRepository is queried, and what "validation"/"mutation" mean when every list is a static, immutable seed. Include triggers: "what's the structure of", "how do I query", "what fields does", "entity diagram", "add a model".
---

## PRIMARY ENTITIES
All in `model/`, plain `data class`es, no Room/Retrofit annotations anywhere.

- **VMInstance** (`model/VMInstance.kt`): `id, name, zone, machineType, status: VMStatus, internalIp, externalIp, createdAt, diskSizeGb: Int = 10, osImage: String = "Debian GNU/Linux 11"`. `VMStatus` enum: `RUNNING, STOPPED, PROVISIONING, STAGING, STOPPING, TERMINATED, SUSPENDED`.
- **StorageBucket** (`model/StorageBucket.kt`): `name, location, storageClass, createdAt, publicAccess: String = "Not public", objectCount: Int = 0, sizeBytes: Long = 0L`.
- **GKECluster**: `name, location, masterVersion, nodeCount: Int, status: String, totalVCPUs: Int, totalMemoryGb: Int`.
- **CloudFunction**: `name, region, runtime, trigger, status: String, lastDeployed, memoryMb: Int = 256`.
- **BigQueryDataset**: `id, name, location, tableCount: Int, lastModified`. **BigQueryTable**: `datasetId, tableId, type, rowCount: Long, sizeBytes: Long, lastModified` (not seeded in `MockDataRepository`).
- **CloudSQLInstance**: `name, databaseVersion, region, tier, status: String, publicIp, storageGb: Int`.
- **IAMMember**: `email, role, type: MemberType` (`USER, SERVICE_ACCOUNT, GROUP, DOMAIN`).
- **APIService**: `name, title, description, enabled: Boolean, category`.
- **BillingProject**: `projectId, projectName, billingAccountName, currentMonthCost: Double, forecastedCost: Double, budgetAmount: Double?` (not seeded).
- **LogEntry**: `timestamp, severity: LogSeverity, resource, message, projectId`. `LogSeverity`: `DEFAULT..EMERGENCY` (9 levels, Cloud-Logging-style).
- **GCPProject**: `id, name, projectNumber, status: String = "ACTIVE"`.
- **MetricDataPoint**: `label, value: Double, unit` (not seeded; used ad hoc by `MonitoringFragment`).

## RELATIONSHIPS
Everything is flat and denormalized — no foreign keys, just loosely-correlated string fields: `LogEntry.resource` embeds a resource-type prefix (`"gce_instance/web-server-1"`, `"cloudsql/prod-mysql"`) that *names* a `VMInstance`/`CloudSQLInstance` by convention only, with no compiler or runtime link back to those lists. `LogEntry.projectId` similarly free-floats against `GCPProject.id` without a join. `CloudFunctionAdapter`'s status text and `LogEntry` for `auth-webhook` are the only two places that agree a function failed — nothing enforces that agreement.

## VALIDATION RULES
This is static seed data with no runtime input, so there's no validation logic to document beyond schema shape. The one real inconsistency — some `status` fields are enums (`VMInstance`, `IAMMember.type`) and some are unchecked `String` (`GKECluster`, `CloudFunction`, `CloudSQLInstance`, `GCPProject`) — is `cloudconsole-mock-review`'s finding (§4); this skill just documents which fields are which type so you don't have to re-derive it.

## QUERY PATTERNS
`model/MockDataRepository` is a Kotlin `object`; every list is a public `val ... = listOf(...)` read directly by fragments (e.g. `MockDataRepository.vmInstances` in `ComputeEngineFragment.onViewCreated`). There is no DAO/repository interface, no filtering/sorting method, no suspend function — access is a plain property read, in-memory, synchronous, on the main thread. Any derived view (counts, "N running") is computed inline at the call site with `.count { }`/`.sumOf { }`, not memoized on the model.

## MUTATIONS
None of these lists are ever written to. "Actions" (start/stop/delete/create) are toast-only per `cloudconsole-mock-review` §2 — see that skill for the detail; this file only notes it so a new model author doesn't go looking for a write path that doesn't exist. Adding a genuinely mutable list (e.g. backing `CreateVMActivity`'s form with a real insert) is an architecture change, not a data-model addition.
