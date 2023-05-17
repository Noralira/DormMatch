package com.example.dormmatch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dormmatch.databinding.MenuBottomNavbarBinding
import com.example.dormmatch.fragments.*

class MenuActivity: AppCompatActivity() {
    private lateinit var binding: MenuBottomNavbarBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MenuBottomNavbarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home-> replaceFragment(Home())
                R.id.favourite-> replaceFragment(Favourite())
                R.id.map-> replaceFragment(Map())
                R.id.settings-> replaceFragment(Settings())

                else ->{}
            }
            true

        }

        val user = intent.getStringExtra("user")
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =  fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

}