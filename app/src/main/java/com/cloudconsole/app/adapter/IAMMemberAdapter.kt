package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.databinding.ItemIamMemberBinding
import com.cloudconsole.app.model.IAMMember
import com.cloudconsole.app.model.MemberType

class IAMMemberAdapter(private val members: List<IAMMember>) :
    RecyclerView.Adapter<IAMMemberAdapter.MemberViewHolder>() {

    inner class MemberViewHolder(val binding: ItemIamMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = ItemIamMemberBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        with(holder.binding) {
            tvMemberEmail.text = member.email
            tvMemberRole.text = member.role
            tvMemberType.text = when (member.type) {
                MemberType.USER -> "👤 User"
                MemberType.SERVICE_ACCOUNT -> "⚙ Service Account"
                MemberType.GROUP -> "👥 Group"
                MemberType.DOMAIN -> "🌐 Domain"
            }
        }
    }

    override fun getItemCount() = members.size
}
