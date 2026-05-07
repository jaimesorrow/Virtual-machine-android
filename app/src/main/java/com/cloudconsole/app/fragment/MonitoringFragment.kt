package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloudconsole.app.databinding.FragmentMonitoringBinding

class MonitoringFragment : Fragment() {

    private var _binding: FragmentMonitoringBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonitoringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateMetrics()
    }

    private fun populateMetrics() {
        // CPU metrics
        binding.tvCpuUsage.text = "24.5%"
        binding.tvCpuPeak.text = "Peak: 87.3%"

        // Memory
        binding.tvMemoryUsage.text = "68.2%"
        binding.tvMemoryPeak.text = "Peak: 91.1%"

        // Network
        binding.tvNetworkIn.text = "1.2 GB/s"
        binding.tvNetworkOut.text = "0.8 GB/s"

        // Disk
        binding.tvDiskRead.text = "45 MB/s"
        binding.tvDiskWrite.text = "23 MB/s"

        // Alerts
        binding.tvActiveAlerts.text = "2 active alerts"
        binding.tvAlert1.text = "⚠ HIGH CPU: prod-mysql (94% for 5 min)"
        binding.tvAlert2.text = "⚠ FUNCTION FAILURE: auth-webhook (3 errors in 1 hour)"

        // Uptime
        binding.tvUptime.text = "99.97%"
        binding.tvUptimePeriod.text = "Last 30 days"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
