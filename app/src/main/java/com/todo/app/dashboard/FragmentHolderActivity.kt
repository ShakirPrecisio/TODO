package com.todo.app.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.todo.app.R
import com.todo.app.databinding.ActivityFragmentHolderBinding

class FragmentHolderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentHolderBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = findNavController(R.id.nav_host_fragment)
        navController = navHostFragment.navController
        //navController.navigate(R.id.recommendedFragment)

    }
}