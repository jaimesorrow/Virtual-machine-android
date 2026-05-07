package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.R
import com.cloudconsole.app.databinding.ItemApiServiceBinding
import com.cloudconsole.app.model.APIService

class APIServiceAdapter(private val services: List<APIService>) :
    RecyclerView.Adapter<APIServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(val binding: ItemApiServiceBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemApiServiceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        with(holder.binding) {
            tvApiTitle.text = service.title
            tvApiName.text = service.name
            tvApiDescription.text = service.description
            tvApiCategory.text = service.category

            if (service.enabled) {
                tvApiStatus.text = "Enabled"
                tvApiStatus.setTextColor(root.context.getColor(R.color.status_running))
            } else {
                tvApiStatus.text = "Disabled"
                tvApiStatus.setTextColor(root.context.getColor(R.color.status_stopped))
            }
        }
    }

    override fun getItemCount() = services.size
}
