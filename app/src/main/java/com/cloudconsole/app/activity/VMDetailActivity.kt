package com.cloudconsole.app.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloudconsole.app.R
import com.cloudconsole.app.databinding.ActivityVmDetailBinding

class VMDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVmDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("vm_name") ?: "Unknown"
        supportActionBar?.title = name

        populateDetails()
        setupButtons()
    }

    private fun populateDetails() {
        val name = intent.getStringExtra("vm_name") ?: "Unknown"
        val zone = intent.getStringExtra("vm_zone") ?: "Unknown"
        val machineType = intent.getStringExtra("vm_machine_type") ?: "Unknown"
        val status = intent.getStringExtra("vm_status") ?: "Unknown"
        val internalIp = intent.getStringExtra("vm_internal_ip") ?: "N/A"
        val externalIp = intent.getStringExtra("vm_external_ip") ?: "None"
        val os = intent.getStringExtra("vm_os") ?: "Unknown"
        val diskGb = intent.getIntExtra("vm_disk_gb", 10)
        val created = intent.getStringExtra("vm_created") ?: "Unknown"

        binding.tvDetailName.text = name
        binding.tvDetailZone.text = zone
        binding.tvDetailMachineType.text = machineType
        binding.tvDetailOS.text = os
        binding.tvDetailInternalIP.text = internalIp
        binding.tvDetailExternalIP.text = externalIp.ifEmpty { "None" }
        binding.tvDetailDisk.text = "$diskGb GB"
        binding.tvDetailCreated.text = created

        val (statusText, statusColor) = when (status) {
            "RUNNING" -> "● RUNNING" to getColor(R.color.status_running)
            "STOPPED", "TERMINATED" -> "● $status" to getColor(R.color.status_stopped)
            else -> "● $status" to getColor(R.color.status_pending)
        }
        binding.tvDetailStatus.text = statusText
        binding.tvDetailStatus.setTextColor(statusColor)

        binding.tvSshCommand.text = "gcloud compute ssh $name --zone=$zone"
    }

    private fun setupButtons() {
        val status = intent.getStringExtra("vm_status") ?: "UNKNOWN"
        val name = intent.getStringExtra("vm_name") ?: "Unknown"

        if (status == "RUNNING") {
            binding.btnStartStop.text = getString(R.string.stop_instance)
            binding.btnStartStop.setOnClickListener {
                Toast.makeText(this, "Stopping $name...", Toast.LENGTH_SHORT).show()
            }
        } else {
            binding.btnStartStop.text = getString(R.string.start_instance)
            binding.btnStartStop.setOnClickListener {
                Toast.makeText(this, "Starting $name...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSsh.setOnClickListener {
            Toast.makeText(this, "Opening SSH session to $name...", Toast.LENGTH_SHORT).show()
        }

        binding.btnDelete.setOnClickListener {
            Toast.makeText(this, "Deleting $name...", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
