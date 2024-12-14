@file:Suppress("DEPRECATION")

package com.example.asuapp001.ui.gallery

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.asuapp001.databinding.FragmentGalleryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val database = Firebase.database
    private val myRefStudent: DatabaseReference = database.getReference("LinkURLStudent")
    private val myRefPrepod: DatabaseReference = database.getReference("LinkURLPrepod")
    private var studentPdfUrl = "https://drive.google.com/file/d/1vyobnZOsCxCNHi0lFjlEFijYl-EI2DoO/view?usp=sharing"
    private var teacherPdfUrl = "https://disk.yandex.ru/i/..."
    lateinit var Pref : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Pref = requireActivity()!!.applicationContext.getSharedPreferences("TableTextBd",   Context.MODE_PRIVATE)
        studentPdfUrl = Pref.getString("studentPdfUrl", "https://drive.google.com/file/d/")!!
        teacherPdfUrl = Pref.getString("teacherPdfUrl", "https://drive.google.com/file/d/")!!

        val studentButton: Button = binding.studentButton // кнопка для студента
        val teacherButton: Button = binding.teacherButton // кнопка для преподавателя

        myRefStudent.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                if (value != null)
                {
                    studentPdfUrl = value
                    Savedata(value,"studentPdfUrl")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        myRefPrepod.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                if (value != null)
                {
                    teacherPdfUrl = value
                    Savedata(value,"teacherPdfUrl")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })


        studentButton.setOnClickListener {
            val intent = CustomTabsIntent.Builder()
                .build()

            intent.launchUrl(requireContext(), Uri.parse(studentPdfUrl));
        }

        teacherButton.setOnClickListener {
            val intent = CustomTabsIntent.Builder()
                .build()

            intent.launchUrl(requireContext(), Uri.parse(teacherPdfUrl));
        }

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
