package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloudconsole.app.databinding.FragmentDashboardBinding
import com.cloudconsole.app.model.MockDataRepository

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateDashboard()
    }

    private fun populateDashboard() {
        val vms = MockDataRepository.vmInstances
        val runningVMs = vms.count { it.status.name == "RUNNING" }
        val buckets = MockDataRepository.storageBuckets
        val clusters = MockDataRepository.gkeClusters
        val functions = MockDataRepository.cloudFunctions

        binding.tvVmCount.text = vms.size.toString()
        binding.tvVmRunning.text = "$runningVMs running"
        binding.tvBucketCount.text = buckets.size.toString()
        val totalStorageGb = buckets.sumOf { it.sizeBytes } / (1024 * 1024 * 1024)
        binding.tvBucketStorage.text = "${totalStorageGb} GB"
        binding.tvClusterCount.text = clusters.size.toString()
        val totalNodes = clusters.sumOf { it.nodeCount }
        binding.tvClusterNodes.text = "$totalNodes nodes"
        binding.tvFunctionCount.text = functions.size.toString()
        val activeFunctions = functions.count { it.status == "ACTIVE" }
        binding.tvFunctionActive.text = "$activeFunctions active"

        binding.tvProjectId.text = "my-project-123"
        binding.tvProjectName.text = "My First Project"
        binding.tvProjectNumber.text = "Project #123456789012"

        // Billing summary
        binding.tvMonthlyCost.text = "$2,847.32"
        binding.tvForecast.text = "Forecast: $3,200.00"

        // Recent activity
        val logs = MockDataRepository.logEntries.take(5)
        val activityText = StringBuilder()
        for (log in logs) {
            activityText.append("${log.timestamp} — ${log.severity.name}: ${log.message}\n\n")
        }
        binding.tvRecentActivity.text = activityText.toString().trimEnd()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
