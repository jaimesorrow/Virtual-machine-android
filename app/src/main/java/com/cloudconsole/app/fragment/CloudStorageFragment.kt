package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.StorageBucketAdapter
import com.cloudconsole.app.databinding.FragmentCloudStorageBinding
import com.cloudconsole.app.model.MockDataRepository

class CloudStorageFragment : Fragment() {

    private var _binding: FragmentCloudStorageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCloudStorageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buckets = MockDataRepository.storageBuckets
        binding.recyclerBuckets.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerBuckets.adapter = StorageBucketAdapter(buckets)

        val totalSizeGb = buckets.sumOf { it.sizeBytes } / (1024L * 1024L * 1024L)
        val totalObjects = buckets.sumOf { it.objectCount }
        binding.tvStorageSummary.text =
            "${buckets.size} buckets · $totalObjects objects · ${totalSizeGb} GB total"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
