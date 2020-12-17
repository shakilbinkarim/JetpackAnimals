package io.github.shakilbinkarim.jetpackanimals.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import io.github.shakilbinkarim.jetpackanimals.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dealWithActionBArWithNavController()
    }

    override fun onSupportNavigateUp(): Boolean = NavigationUI.navigateUp(navController, null)

    private fun dealWithActionBArWithNavController() {
        navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

}