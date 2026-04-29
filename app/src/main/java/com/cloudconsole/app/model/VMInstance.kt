package com.cloudconsole.app.model

data class VMInstance(
    val id: String,
    val name: String,
    val zone: String,
    val machineType: String,
    val status: VMStatus,
    val internalIp: String,
    val externalIp: String,
    val createdAt: String,
    val diskSizeGb: Int = 10,
    val osImage: String = "Debian GNU/Linux 11"
)

enum class VMStatus {
    RUNNING, STOPPED, PROVISIONING, STAGING, STOPPING, TERMINATED, SUSPENDED
}
