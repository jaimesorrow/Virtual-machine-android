package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.CloudFunctionAdapter
import com.cloudconsole.app.databinding.FragmentCloudFunctionsBinding
import com.cloudconsole.app.model.MockDataRepository

class CloudFunctionsFragment : Fragment() {

    private var _binding: FragmentCloudFunctionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCloudFunctionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val functions = MockDataRepository.cloudFunctions
        binding.recyclerFunctions.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerFunctions.adapter = CloudFunctionAdapter(functions)

        val active = functions.count { it.status == "ACTIVE" }
        binding.tvFunctionsSummary.text =
            "${functions.size} functions · $active active"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
