package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloudconsole.app.databinding.FragmentCloudRunBinding

class CloudRunFragment : Fragment() {

    private var _binding: FragmentCloudRunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCloudRunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateCloudRun()
    }

    private fun populateCloudRun() {
        binding.tvServiceName1.text = "api-service"
        binding.tvServiceRegion1.text = "us-central1"
        binding.tvServiceUrl1.text = "https://api-service-abc123-uc.a.run.app"
        binding.tvServiceRequests1.text = "12,345 req/day"
        binding.tvServiceStatus1.text = "Serving"

        binding.tvServiceName2.text = "web-frontend"
        binding.tvServiceRegion2.text = "us-central1"
        binding.tvServiceUrl2.text = "https://web-frontend-xyz789-uc.a.run.app"
        binding.tvServiceRequests2.text = "45,678 req/day"
        binding.tvServiceStatus2.text = "Serving"

        binding.tvServiceName3.text = "data-pipeline"
        binding.tvServiceRegion3.text = "us-east1"
        binding.tvServiceUrl3.text = "https://data-pipeline-def456-ue.a.run.app"
        binding.tvServiceRequests3.text = "2,890 req/day"
        binding.tvServiceStatus3.text = "Serving"

        binding.tvRunSummary.text = "3 services · 3 serving"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
