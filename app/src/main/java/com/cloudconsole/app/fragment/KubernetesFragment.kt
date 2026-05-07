package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.GKEClusterAdapter
import com.cloudconsole.app.databinding.FragmentKubernetesBinding
import com.cloudconsole.app.model.MockDataRepository

class KubernetesFragment : Fragment() {

    private var _binding: FragmentKubernetesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKubernetesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clusters = MockDataRepository.gkeClusters
        binding.recyclerClusters.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerClusters.adapter = GKEClusterAdapter(clusters)

        val totalNodes = clusters.sumOf { it.nodeCount }
        val totalVCPUs = clusters.sumOf { it.totalVCPUs }
        binding.tvClusterSummary.text =
            "${clusters.size} clusters · $totalNodes nodes · $totalVCPUs vCPUs"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
