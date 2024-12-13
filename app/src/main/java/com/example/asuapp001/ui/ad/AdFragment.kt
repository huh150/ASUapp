package com.example.asuapp001.ui.ad

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.icu.text.CaseMap.Title
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.bumptech.glide.Glide
import com.example.asuapp001.R
import com.example.asuapp001.databinding.ActivityMainBinding
import com.example.asuapp001.databinding.FragmentAdBinding
import com.example.asuapp001.databinding.FragmentHomeBinding
import com.example.asuapp001.ui.home.HomeViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class AdFragment : Fragment() {

    private var _binding: FragmentAdBinding? = null
    private val binding get() = _binding!!

    lateinit var TextFromDb : TextView
    lateinit var ImageViewFromBd : ImageView
    var ImgURL : String  = ""

    lateinit var Pref : SharedPreferences

    companion object {
        fun newInstance() = AdFragment()
    }

    private val viewModel: AdViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View
    {
        val AdViewModel =
            ViewModelProvider(this).get(AdViewModel::class.java)

        _binding = FragmentAdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Pref = requireActivity()!!.applicationContext.getSharedPreferences("TableTextBd",   Context.MODE_PRIVATE)

        val database = Firebase.database
        val myRef = database.getReference("message")
        val myRefImg = database.getReference("ImageUrl")
        val myRefBool = database.getReference("VisibleImage")


        TextFromDb = binding.TextFromDB
        ImageViewFromBd = binding.imageFromBD
        TextFromDb.text = Pref.getString("TextFromDb","Пусто")

        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = snapshot.getValue<String>()
                if (value != null)
                {
                    TextFromDb.text = value
                    Savedata(value, "TextFromDb")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

        val ActContext = context
        if (ActContext != null)
        {
            Glide.with(ActContext)
                .load(ImgURL)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(ImageViewFromBd)
        }

        myRefImg.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                val value = snapshot.getValue<String>()
                if (value != null)
                {
                    ImgURL = value
                    Savedata(value,"ImgURLData")
                    val ActContext = context
                    if (ActContext != null)
                    {
                        Glide.with(ActContext)
                        .load(ImgURL)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(ImageViewFromBd)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError)
            {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        myRefBool.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<Boolean>()
                if (value == true)
                {
                    ImageViewFromBd.visibility = View.VISIBLE
                }
                else if (value == false)
                {
                    ImageViewFromBd.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError)
            {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun Savedata(data : String, key : String)
    {
        val editor = Pref?.edit()
        editor?.putString(key, data)
        editor?.apply()
    }
}