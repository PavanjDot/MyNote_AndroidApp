package com.example.mynote.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.mynote.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navcontroller = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navcontroller)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(

            Navigation.findNavController(this, R.id.fragment),

            null


        )
    }
}
