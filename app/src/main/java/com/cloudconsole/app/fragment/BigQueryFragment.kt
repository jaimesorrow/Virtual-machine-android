package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.BigQueryDatasetAdapter
import com.cloudconsole.app.databinding.FragmentBigqueryBinding
import com.cloudconsole.app.model.MockDataRepository

class BigQueryFragment : Fragment() {

    private var _binding: FragmentBigqueryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBigqueryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datasets = MockDataRepository.bigQueryDatasets
        binding.recyclerDatasets.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerDatasets.adapter = BigQueryDatasetAdapter(datasets)

        val totalTables = datasets.sumOf { it.tableCount }
        binding.tvDatasetSummary.text =
            "${datasets.size} datasets · $totalTables tables"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
