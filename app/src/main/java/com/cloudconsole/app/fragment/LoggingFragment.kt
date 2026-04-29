package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.LogEntryAdapter
import com.cloudconsole.app.databinding.FragmentLoggingBinding
import com.cloudconsole.app.model.MockDataRepository

class LoggingFragment : Fragment() {

    private var _binding: FragmentLoggingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoggingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logs = MockDataRepository.logEntries
        binding.recyclerLogs.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerLogs.adapter = LogEntryAdapter(logs)

        val errors = logs.count { it.severity.name in listOf("ERROR", "CRITICAL", "ALERT", "EMERGENCY") }
        val warnings = logs.count { it.severity.name == "WARNING" }
        binding.tvLogSummary.text =
            "${logs.size} log entries · $errors errors · $warnings warnings"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
