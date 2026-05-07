package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.databinding.ItemStorageBucketBinding
import com.cloudconsole.app.model.StorageBucket

class StorageBucketAdapter(private val buckets: List<StorageBucket>) :
    RecyclerView.Adapter<StorageBucketAdapter.BucketViewHolder>() {

    inner class BucketViewHolder(val binding: ItemStorageBucketBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BucketViewHolder {
        val binding = ItemStorageBucketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BucketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BucketViewHolder, position: Int) {
        val bucket = buckets[position]
        with(holder.binding) {
            tvBucketName.text = bucket.name
            tvBucketLocation.text = bucket.location
            tvBucketClass.text = bucket.storageClass
            tvBucketPublicAccess.text = bucket.publicAccess
            tvBucketObjects.text = "${bucket.objectCount} objects"
            val sizeText = when {
                bucket.sizeBytes >= 1_000_000_000_000L -> "${bucket.sizeBytes / 1_000_000_000_000L} TB"
                bucket.sizeBytes >= 1_000_000_000L -> "${bucket.sizeBytes / 1_000_000_000L} GB"
                bucket.sizeBytes >= 1_000_000L -> "${bucket.sizeBytes / 1_000_000L} MB"
                else -> "${bucket.sizeBytes / 1_000L} KB"
            }
            tvBucketSize.text = sizeText
            tvBucketCreated.text = "Created: ${bucket.createdAt}"
        }
    }

    override fun getItemCount() = buckets.size
}
