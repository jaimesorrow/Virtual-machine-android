package com.cloudconsole.app

import com.cloudconsole.app.model.*
import org.junit.Test
import org.junit.Assert.*

class MockDataRepositoryTest {

    @Test
    fun `vmInstances contains expected data`() {
        val vms = MockDataRepository.vmInstances
        assertTrue("Should have VM instances", vms.isNotEmpty())
        assertEquals("Should have 5 VMs", 5, vms.size)
        val running = vms.count { it.status == VMStatus.RUNNING }
        assertTrue("Should have running VMs", running > 0)
    }

    @Test
    fun `storageBuckets contains expected data`() {
        val buckets = MockDataRepository.storageBuckets
        assertTrue("Should have storage buckets", buckets.isNotEmpty())
        assertEquals("Should have 5 buckets", 5, buckets.size)
    }

    @Test
    fun `gkeClusters contains expected data`() {
        val clusters = MockDataRepository.gkeClusters
        assertTrue("Should have GKE clusters", clusters.isNotEmpty())
        val totalNodes = clusters.sumOf { it.nodeCount }
        assertTrue("Should have nodes", totalNodes > 0)
    }

    @Test
    fun `cloudFunctions contains expected data`() {
        val functions = MockDataRepository.cloudFunctions
        assertTrue("Should have cloud functions", functions.isNotEmpty())
        val active = functions.count { it.status == "ACTIVE" }
        assertTrue("Should have active functions", active > 0)
    }

    @Test
    fun `apiServices contains expected data`() {
        val services = MockDataRepository.apiServices
        assertTrue("Should have API services", services.isNotEmpty())
        val enabled = services.count { it.enabled }
        assertTrue("Should have enabled services", enabled > 0)
    }

    @Test
    fun `iamMembers has correct types`() {
        val members = MockDataRepository.iamMembers
        assertTrue("Should have IAM members", members.isNotEmpty())
        assertTrue("Should have users", members.any { it.type == MemberType.USER })
        assertTrue("Should have service accounts",
            members.any { it.type == MemberType.SERVICE_ACCOUNT })
    }

    @Test
    fun `logEntries has entries of various severities`() {
        val logs = MockDataRepository.logEntries
        assertTrue("Should have log entries", logs.isNotEmpty())
        val hasError = logs.any {
            it.severity in listOf(LogSeverity.ERROR, LogSeverity.CRITICAL)
        }
        assertTrue("Should have error-level logs", hasError)
    }

    @Test
    fun `projects list is populated`() {
        val projects = MockDataRepository.projects
        assertEquals("Should have 3 projects", 3, projects.size)
        assertTrue("All projects should have IDs",
            projects.all { it.id.isNotEmpty() })
    }

    @Test
    fun `vmInstance data model is correct`() {
        val vm = VMInstance(
            id = "test-001",
            name = "test-vm",
            zone = "us-central1-a",
            machineType = "e2-medium",
            status = VMStatus.RUNNING,
            internalIp = "10.0.0.1",
            externalIp = "1.2.3.4",
            createdAt = "2024-01-01"
        )
        assertEquals("test-vm", vm.name)
        assertEquals(VMStatus.RUNNING, vm.status)
        assertEquals(10, vm.diskSizeGb)
    }

    @Test
    fun `storage bucket size formatting logic`() {
        val bucket = StorageBucket(
            name = "test-bucket",
            location = "US",
            storageClass = "STANDARD",
            createdAt = "2024-01-01",
            sizeBytes = 2_000_000_000L
        )
        assertTrue("Size should be positive", bucket.sizeBytes > 0)
        val sizeGb = bucket.sizeBytes / 1_000_000_000L
        assertEquals(2L, sizeGb)
    }
}
