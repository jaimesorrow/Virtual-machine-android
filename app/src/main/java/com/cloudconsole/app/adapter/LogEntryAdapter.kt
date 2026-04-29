package com.cloudconsole.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudconsole.app.R
import com.cloudconsole.app.databinding.ItemLogEntryBinding
import com.cloudconsole.app.model.LogEntry
import com.cloudconsole.app.model.LogSeverity

class LogEntryAdapter(private val entries: List<LogEntry>) :
    RecyclerView.Adapter<LogEntryAdapter.LogViewHolder>() {

    inner class LogViewHolder(val binding: ItemLogEntryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val binding = ItemLogEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return LogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val entry = entries[position]
        with(holder.binding) {
            tvLogTimestamp.text = entry.timestamp
            tvLogResource.text = entry.resource
            tvLogMessage.text = entry.message

            val (severityText, severityColor) = when (entry.severity) {
                LogSeverity.ERROR, LogSeverity.CRITICAL,
                LogSeverity.ALERT, LogSeverity.EMERGENCY ->
                    entry.severity.name to root.context.getColor(R.color.status_error)
                LogSeverity.WARNING ->
                    entry.severity.name to root.context.getColor(R.color.status_warning)
                LogSeverity.INFO, LogSeverity.NOTICE ->
                    entry.severity.name to root.context.getColor(R.color.status_running)
                else ->
                    entry.severity.name to root.context.getColor(R.color.text_secondary)
            }
            tvLogSeverity.text = severityText
            tvLogSeverity.setTextColor(severityColor)
        }
    }

    override fun getItemCount() = entries.size
}
