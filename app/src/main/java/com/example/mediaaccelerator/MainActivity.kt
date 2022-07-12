package com.example.mediaaccelerator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.get
import com.example.mediaaccelerator.databinding.ActivityMainBinding
import io.ak1.pix.helpers.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.navigate(R.id.navigation_home)
        PixBus.results {
            if (it.status == PixEventCallback.Status.SUCCESS) {
                showStatusBar()
                navController.navigateUp()
            }
        }
    }

    override fun onBackPressed() {
        if (navController.currentDestination == navController.graph[R.id.CameraFragment]) {
            PixBus.onBackPressedEvent()
        } else if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}




