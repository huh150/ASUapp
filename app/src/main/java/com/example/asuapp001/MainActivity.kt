package com.example.asuapp001

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.navigation.NavOptions
import com.example.asuapp001.databinding.ActivityMainBinding
import com.example.asuapp001.ui.ad.AdFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var MytextBar : TextView
    lateinit var pref : SharedPreferences
    val database = Firebase.database
    val myRef = database.getReference("message")
    lateinit var MainValueDB : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("MainActTable" , Context.MODE_PRIVATE)
        MainValueDB = pref.getString("dataMainValue", "ds")!!

            setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .setAnchorView(R.id.fab).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.menu_dailyPlanner, R.id.menu_creators , R.id.menu_ad , R.id.menu_admin, R.id.menu_bopros,
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


       MytextBar = MenuItemCompat.getActionView(navView.menu.findItem(R.id.menu_ad)) as TextView
       MytextBar.setGravity(Gravity.CENTER_VERTICAL);
       MytextBar.text = pref.getString("dataMainValueTTT","")!!

        val navOptions = NavOptions.Builder()
            .setLaunchSingleTop(false)
            .build()

        val menuItem: MenuItem = navView.menu.findItem(R.id.menu_ad)
        menuItem.setOnMenuItemClickListener{menuItem ->
            MytextBar.text = ""
            Savedata(MytextBar.text.toString(), "dataMainValueTTT")
            navController.popBackStack()
            navController.navigate(R.id.menu_ad, null, navOptions)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                if (value != null)
                {
                    if (MainValueDB != value)
                    {
                        MainValueDB = value
                        Savedata(value,"dataMainValue")
                        MytextBar.text = "⁉️"
                        Savedata(MytextBar.text.toString(),"dataMainValueTTT")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun Savedata(data : String, key : String)
    {
        val editor = pref?.edit()
        editor?.putString(key, data)
        editor?.apply()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}