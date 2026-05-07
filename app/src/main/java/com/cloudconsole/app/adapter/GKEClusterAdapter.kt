package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.databinding.ItemGkeClusterBinding
import com.cloudconsole.app.model.GKECluster

class GKEClusterAdapter(private val clusters: List<GKECluster>) :
    RecyclerView.Adapter<GKEClusterAdapter.ClusterViewHolder>() {

    inner class ClusterViewHolder(val binding: ItemGkeClusterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClusterViewHolder {
        val binding = ItemGkeClusterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ClusterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClusterViewHolder, position: Int) {
        val cluster = clusters[position]
        with(holder.binding) {
            tvClusterName.text = cluster.name
            tvClusterLocation.text = cluster.location
            tvClusterVersion.text = "v${cluster.masterVersion}"
            tvClusterNodes.text = "${cluster.nodeCount} nodes"
            tvClusterCpus.text = "${cluster.totalVCPUs} vCPUs · ${cluster.totalMemoryGb} GB RAM"
            tvClusterStatus.text = cluster.status
        }
    }

    override fun getItemCount() = clusters.size
}
