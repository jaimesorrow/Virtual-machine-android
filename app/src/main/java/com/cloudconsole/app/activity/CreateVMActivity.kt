package com.cloudconsole.app.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloudconsole.app.databinding.ActivityCreateVmBinding

class CreateVMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateVmBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateVmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDropdowns()
        setupButtons()
    }

    private fun setupDropdowns() {
        val regions = listOf(
            "us-central1-a", "us-central1-b", "us-central1-c",
            "us-east1-b", "us-east1-c", "us-east1-d",
            "us-west1-a", "us-west1-b",
            "europe-west1-b", "europe-west1-c",
            "asia-east1-a", "asia-east1-b"
        )
        val regionAdapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line, regions)
        binding.spinnerZone.setAdapter(regionAdapter)

        val machineTypes = listOf(
            "e2-micro (0.25 vCPU, 1 GB RAM)",
            "e2-small (0.5 vCPU, 2 GB RAM)",
            "e2-medium (1 vCPU, 4 GB RAM)",
            "e2-standard-2 (2 vCPUs, 8 GB RAM)",
            "e2-standard-4 (4 vCPUs, 16 GB RAM)",
            "n2-standard-2 (2 vCPUs, 8 GB RAM)",
            "n2-standard-4 (4 vCPUs, 16 GB RAM)",
            "n2-standard-8 (8 vCPUs, 32 GB RAM)",
            "n1-highmem-4 (4 vCPUs, 26 GB RAM)",
            "n1-highmem-8 (8 vCPUs, 52 GB RAM)"
        )
        val machineAdapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line, machineTypes)
        binding.spinnerMachineType.setAdapter(machineAdapter)

        val osImages = listOf(
            "Debian GNU/Linux 11 (bullseye)",
            "Debian GNU/Linux 12 (bookworm)",
            "Ubuntu 20.04 LTS",
            "Ubuntu 22.04 LTS",
            "CentOS 7",
            "Rocky Linux 8",
            "Windows Server 2022",
            "Container-Optimized OS"
        )
        val osAdapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line, osImages)
        binding.spinnerOSImage.setAdapter(osAdapter)

        val diskTypes = listOf(
            "Balanced persistent disk (pd-balanced)",
            "SSD persistent disk (pd-ssd)",
            "Standard persistent disk (pd-standard)",
            "Extreme persistent disk (pd-extreme)"
        )
        val diskAdapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line, diskTypes)
        binding.spinnerDiskType.setAdapter(diskAdapter)
    }

    private fun setupButtons() {
        binding.btnCreate.setOnClickListener {
            val name = binding.etVMName.text.toString().trim()
            if (name.isEmpty()) {
                binding.etVMName.error = "VM name is required"
                return@setOnClickListener
            }
            Toast.makeText(
                this,
                "Creating VM instance '$name'...",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }

        binding.btnCancel.setOnClickListener {
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
