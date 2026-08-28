package com.cloudconsole.app.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.activity.CreateVMActivity
import com.cloudconsole.app.adapter.VMInstanceAdapter
import com.cloudconsole.app.databinding.FragmentComputeEngineBinding
import com.cloudconsole.app.model.MockDataRepository

class ComputeEngineFragment : Fragment() {

    private var _binding: FragmentComputeEngineBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComputeEngineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val instances = MockDataRepository.vmInstances
        binding.recyclerVms.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerVms.adapter = VMInstanceAdapter(instances)

        binding.fabCreateVm.setOnClickListener {
            startActivity(Intent(requireContext(), CreateVMActivity::class.java))
        }

        val running = instances.count { it.status.name == "RUNNING" }
        binding.tvInstancesSummary.text = "${instances.size} instances · $running running"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
