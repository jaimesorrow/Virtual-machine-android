package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.APIServiceAdapter
import com.cloudconsole.app.databinding.FragmentApiServicesBinding
import com.cloudconsole.app.model.MockDataRepository

class APIServicesFragment : Fragment() {

    private var _binding: FragmentApiServicesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentApiServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val services = MockDataRepository.apiServices
        binding.recyclerApis.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerApis.adapter = APIServiceAdapter(services)

        val enabled = services.count { it.enabled }
        binding.tvApisSummary.text =
            "${services.size} APIs available · $enabled enabled"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
