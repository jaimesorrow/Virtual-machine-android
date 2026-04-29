package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.R
import com.cloudconsole.app.databinding.ItemCloudFunctionBinding
import com.cloudconsole.app.model.CloudFunction

class CloudFunctionAdapter(private val functions: List<CloudFunction>) :
    RecyclerView.Adapter<CloudFunctionAdapter.FunctionViewHolder>() {

    inner class FunctionViewHolder(val binding: ItemCloudFunctionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionViewHolder {
        val binding = ItemCloudFunctionBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FunctionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FunctionViewHolder, position: Int) {
        val function = functions[position]
        with(holder.binding) {
            tvFunctionName.text = function.name
            tvFunctionRegion.text = function.region
            tvFunctionRuntime.text = function.runtime
            tvFunctionTrigger.text = "Trigger: ${function.trigger}"
            tvFunctionMemory.text = "${function.memoryMb} MB"
            tvFunctionLastDeployed.text = "Deployed: ${function.lastDeployed}"

            val (statusText, statusColor) = when (function.status) {
                "ACTIVE" -> "● ACTIVE" to root.context.getColor(R.color.status_running)
                "FAILED" -> "● FAILED" to root.context.getColor(R.color.status_error)
                else -> "● ${function.status}" to root.context.getColor(R.color.status_pending)
            }
            tvFunctionStatus.text = statusText
            tvFunctionStatus.setTextColor(statusColor)
        }
    }

    override fun getItemCount() = functions.size
}
