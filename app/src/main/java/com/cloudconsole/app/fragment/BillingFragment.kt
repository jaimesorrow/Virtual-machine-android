package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloudconsole.app.databinding.FragmentBillingBinding

class BillingFragment : Fragment() {

    private var _binding: FragmentBillingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBillingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateBilling()
    }

    private fun populateBilling() {
        binding.tvCurrentMonthCost.text = "$2,847.32"
        binding.tvLastMonthCost.text = "$2,612.45"
        binding.tvForecastedCost.text = "$3,200.00"
        binding.tvBudgetAmount.text = "$3,500.00"
        binding.tvBudgetUsed.text = "81% of budget used"

        // Top services by cost
        binding.tvComputeCost.text = "$1,456.80"
        binding.tvStorageCost.text = "$342.15"
        binding.tvNetworkingCost.text = "$289.44"
        binding.tvKubernetesCost.text = "$512.30"
        binding.tvOtherCost.text = "$246.63"

        // Credits
        binding.tvCreditsApplied.text = "-$300.00"
        binding.tvNetCost.text = "$2,547.32"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
