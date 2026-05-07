package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.R
import com.cloudconsole.app.databinding.ItemCloudSqlBinding
import com.cloudconsole.app.model.CloudSQLInstance

class CloudSQLAdapter(private val instances: List<CloudSQLInstance>) :
    RecyclerView.Adapter<CloudSQLAdapter.SQLViewHolder>() {

    inner class SQLViewHolder(val binding: ItemCloudSqlBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SQLViewHolder {
        val binding = ItemCloudSqlBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SQLViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SQLViewHolder, position: Int) {
        val instance = instances[position]
        with(holder.binding) {
            tvSqlName.text = instance.name
            tvSqlVersion.text = instance.databaseVersion
            tvSqlRegion.text = instance.region
            tvSqlTier.text = instance.tier
            tvSqlPublicIp.text = if (instance.publicIp.isNotEmpty())
                "IP: ${instance.publicIp}" else "IP: Private only"
            tvSqlStorage.text = "${instance.storageGb} GB"

            val (statusText, statusColor) = when (instance.status) {
                "RUNNABLE" -> "● RUNNING" to root.context.getColor(R.color.status_running)
                "SUSPENDED" -> "● SUSPENDED" to root.context.getColor(R.color.status_stopped)
                else -> "● ${instance.status}" to root.context.getColor(R.color.status_pending)
            }
            tvSqlStatus.text = statusText
            tvSqlStatus.setTextColor(statusColor)
        }
    }

    override fun getItemCount() = instances.size
}
