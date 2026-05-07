package com.cloudconsole.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudconsole.app.adapter.IAMMemberAdapter
import com.cloudconsole.app.databinding.FragmentIamBinding
import com.cloudconsole.app.model.MockDataRepository

class IAMFragment : Fragment() {

    private var _binding: FragmentIamBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIamBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val members = MockDataRepository.iamMembers
        binding.recyclerMembers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerMembers.adapter = IAMMemberAdapter(members)

        val users = members.count { it.type.name == "USER" }
        val serviceAccounts = members.count { it.type.name == "SERVICE_ACCOUNT" }
        binding.tvIamSummary.text =
            "${members.size} principals · $users users · $serviceAccounts service accounts"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
