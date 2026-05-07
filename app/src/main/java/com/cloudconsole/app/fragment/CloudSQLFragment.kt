package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.CloudSQLAdapter
import com.cloudconsole.app.databinding.FragmentCloudSqlBinding
import com.cloudconsole.app.model.MockDataRepository

class CloudSQLFragment : Fragment() {

    private var _binding: FragmentCloudSqlBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCloudSqlBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val instances = MockDataRepository.cloudSQLInstances
        binding.recyclerSqlInstances.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSqlInstances.adapter = CloudSQLAdapter(instances)

        val running = instances.count { it.status == "RUNNABLE" }
        binding.tvSqlSummary.text = "${instances.size} instances · $running running"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
