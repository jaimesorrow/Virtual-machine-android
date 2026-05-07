package com.cloudconsole.app.model

data class StorageBucket(
    val name: String,
    val location: String,
    val storageClass: String,
    val createdAt: String,
    val publicAccess: String = "Not public",
    val objectCount: Int = 0,
    val sizeBytes: Long = 0L
)
