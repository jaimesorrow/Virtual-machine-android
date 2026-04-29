package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.databinding.ItemBigqueryDatasetBinding
import com.cloudconsole.app.model.BigQueryDataset

class BigQueryDatasetAdapter(private val datasets: List<BigQueryDataset>) :
    RecyclerView.Adapter<BigQueryDatasetAdapter.DatasetViewHolder>() {

    inner class DatasetViewHolder(val binding: ItemBigqueryDatasetBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatasetViewHolder {
        val binding = ItemBigqueryDatasetBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DatasetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DatasetViewHolder, position: Int) {
        val dataset = datasets[position]
        with(holder.binding) {
            tvDatasetName.text = dataset.name
            tvDatasetId.text = dataset.id
            tvDatasetLocation.text = dataset.location
            tvDatasetTables.text = "${dataset.tableCount} tables"
            tvDatasetLastModified.text = "Modified: ${dataset.lastModified}"
        }
    }

    override fun getItemCount() = datasets.size
}
