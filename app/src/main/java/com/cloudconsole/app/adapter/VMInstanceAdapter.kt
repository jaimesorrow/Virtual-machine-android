package com.cloudconsole.app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.R
import com.cloudconsole.app.activity.VMDetailActivity
import com.cloudconsole.app.databinding.ItemVmInstanceBinding
import com.cloudconsole.app.model.VMInstance
import com.cloudconsole.app.model.VMStatus

class VMInstanceAdapter(private val instances: List<VMInstance>) :
    RecyclerView.Adapter<VMInstanceAdapter.VMViewHolder>() {

    inner class VMViewHolder(val binding: ItemVmInstanceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VMViewHolder {
        val binding = ItemVmInstanceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VMViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VMViewHolder, position: Int) {
        val instance = instances[position]
        with(holder.binding) {
            tvVmName.text = instance.name
            tvVmZone.text = instance.zone
            tvVmMachineType.text = instance.machineType
            tvVmInternalIp.text = if (instance.internalIp.isNotEmpty())
                "Internal: ${instance.internalIp}" else "Internal: N/A"
            tvVmExternalIp.text = if (instance.externalIp.isNotEmpty())
                "External: ${instance.externalIp}" else "External: None"
            tvVmOsImage.text = instance.osImage

            val (statusText, statusColor) = when (instance.status) {
                VMStatus.RUNNING -> "● RUNNING" to root.context.getColor(R.color.status_running)
                VMStatus.STOPPED -> "● STOPPED" to root.context.getColor(R.color.status_stopped)
                VMStatus.TERMINATED -> "● TERMINATED" to root.context.getColor(R.color.status_stopped)
                VMStatus.PROVISIONING -> "● PROVISIONING" to root.context.getColor(R.color.status_pending)
                VMStatus.STAGING -> "● STAGING" to root.context.getColor(R.color.status_pending)
                VMStatus.STOPPING -> "● STOPPING" to root.context.getColor(R.color.status_pending)
                VMStatus.SUSPENDED -> "● SUSPENDED" to root.context.getColor(R.color.status_stopped)
            }
            tvVmStatus.text = statusText
            tvVmStatus.setTextColor(statusColor)

            root.setOnClickListener {
                val intent = Intent(root.context, VMDetailActivity::class.java).apply {
                    putExtra("vm_name", instance.name)
                    putExtra("vm_zone", instance.zone)
                    putExtra("vm_machine_type", instance.machineType)
                    putExtra("vm_status", instance.status.name)
                    putExtra("vm_internal_ip", instance.internalIp)
                    putExtra("vm_external_ip", instance.externalIp)
                    putExtra("vm_os", instance.osImage)
                    putExtra("vm_disk_gb", instance.diskSizeGb)
                    putExtra("vm_created", instance.createdAt)
                }
                root.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = instances.size
}
