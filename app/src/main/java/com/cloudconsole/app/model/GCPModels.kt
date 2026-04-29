package com.cloudconsole.app.model

data class GKECluster(
    val name: String,
    val location: String,
    val masterVersion: String,
    val nodeCount: Int,
    val status: String,
    val totalVCPUs: Int,
    val totalMemoryGb: Int
)

data class CloudFunction(
    val name: String,
    val region: String,
    val runtime: String,
    val trigger: String,
    val status: String,
    val lastDeployed: String,
    val memoryMb: Int = 256
)

data class BigQueryDataset(
    val id: String,
    val name: String,
    val location: String,
    val tableCount: Int,
    val lastModified: String
)

data class BigQueryTable(
    val datasetId: String,
    val tableId: String,
    val type: String,
    val rowCount: Long,
    val sizeBytes: Long,
    val lastModified: String
)

data class CloudSQLInstance(
    val name: String,
    val databaseVersion: String,
    val region: String,
    val tier: String,
    val status: String,
    val publicIp: String,
    val storageGb: Int
)

data class IAMMember(
    val email: String,
    val role: String,
    val type: MemberType
)

enum class MemberType {
    USER, SERVICE_ACCOUNT, GROUP, DOMAIN
}

data class APIService(
    val name: String,
    val title: String,
    val description: String,
    val enabled: Boolean,
    val category: String
)

data class BillingProject(
    val projectId: String,
    val projectName: String,
    val billingAccountName: String,
    val currentMonthCost: Double,
    val forecastedCost: Double,
    val budgetAmount: Double?
)

data class MetricDataPoint(
    val label: String,
    val value: Double,
    val unit: String
)

data class LogEntry(
    val timestamp: String,
    val severity: LogSeverity,
    val resource: String,
    val message: String,
    val projectId: String
)

enum class LogSeverity {
    DEFAULT, DEBUG, INFO, NOTICE, WARNING, ERROR, CRITICAL, ALERT, EMERGENCY
}

data class GCPProject(
    val id: String,
    val name: String,
    val projectNumber: String,
    val status: String = "ACTIVE"
)
