package com.cloudconsole.app

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.cloudconsole.app.databinding.ActivityMainBinding
import com.cloudconsole.app.fragment.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        // Set header project info
        val headerView = binding.navView.getHeaderView(0)
        headerView.findViewById<TextView>(R.id.nav_header_subtitle)?.text =
            "my-project-123"

        // Load dashboard by default
        if (savedInstanceState == null) {
            loadFragment(DashboardFragment())
            binding.navView.setCheckedItem(R.id.nav_dashboard)
            supportActionBar?.title = getString(R.string.nav_dashboard)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        val title: String

        when (item.itemId) {
            R.id.nav_dashboard -> {
                fragment = DashboardFragment()
                title = getString(R.string.nav_dashboard)
            }
            R.id.nav_compute_engine -> {
                fragment = ComputeEngineFragment()
                title = getString(R.string.nav_compute_engine)
            }
            R.id.nav_cloud_storage -> {
                fragment = CloudStorageFragment()
                title = getString(R.string.nav_cloud_storage)
            }
            R.id.nav_kubernetes -> {
                fragment = KubernetesFragment()
                title = getString(R.string.nav_kubernetes)
            }
            R.id.nav_cloud_functions -> {
                fragment = CloudFunctionsFragment()
                title = getString(R.string.nav_cloud_functions)
            }
            R.id.nav_bigquery -> {
                fragment = BigQueryFragment()
                title = getString(R.string.nav_bigquery)
            }
            R.id.nav_cloud_sql -> {
                fragment = CloudSQLFragment()
                title = getString(R.string.nav_cloud_sql)
            }
            R.id.nav_iam -> {
                fragment = IAMFragment()
                title = getString(R.string.nav_iam)
            }
            R.id.nav_billing -> {
                fragment = BillingFragment()
                title = getString(R.string.nav_billing)
            }
            R.id.nav_apis -> {
                fragment = APIServicesFragment()
                title = getString(R.string.nav_apis)
            }
            R.id.nav_monitoring -> {
                fragment = MonitoringFragment()
                title = getString(R.string.nav_monitoring)
            }
            R.id.nav_logging -> {
                fragment = LoggingFragment()
                title = getString(R.string.nav_logging)
            }
            R.id.nav_cloud_run -> {
                fragment = CloudRunFragment()
                title = getString(R.string.nav_cloud_run)
            }
            else -> {
                fragment = DashboardFragment()
                title = getString(R.string.nav_dashboard)
            }
        }

        loadFragment(fragment)
        supportActionBar?.title = title
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
