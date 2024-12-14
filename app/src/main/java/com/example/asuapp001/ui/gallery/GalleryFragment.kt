@file:Suppress("DEPRECATION")

package com.example.asuapp001.ui.gallery

import android.content.ActivityNotFoundException
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.asuapp001.R
import com.example.asuapp001.databinding.FragmentGalleryBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


fun openPartialCustomTab(context: Context, url: String) {
    // Получение пакета Chrome
    val packageName = getChromePackageName(context)

    // Создание объекта CustomTabsIntent.Builder
    val builder = CustomTabsIntent.Builder()
        .setShowTitle(true) // Отображение заголовка страницы
        .setToolbarColor(context.getColor(R.color.purple_700)) // Цвет панели инструментов

    builder.setInitialActivityHeightPx(500)

    // Создание объекта CustomTabsIntent
    val customTabsIntent = builder.build()

    // Проверка наличия Chrome и поддержки Custom Tabs
    if (packageName != null) {
        customTabsIntent.intent.setPackage(packageName)
        customTabsIntent.launchUrl(context, Uri.parse(url))
    } else {
        openInBrowserFallback(context, url)
    }
}

private fun getChromePackageName(context: Context): String? {
    val packageManager = context.packageManager
    val customTabsPackages = packageManager.queryIntentServices(
        Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION), 0
    )
    for (info in customTabsPackages) {
        if (info.serviceInfo.packageName.equals("com.android.chrome", true)) {
            return info.serviceInfo.packageName
        }
    }
    return null
}

private fun openInBrowserFallback(context: Context, url: String) {
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    } catch (e: ActivityNotFoundException) {
        // Обработка ошибки (например, отображение сообщения пользователю)
    }
}

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val database = Firebase.database
    private val myRefStudent: DatabaseReference = database.getReference("LinkURLStudent")
    private val myRefPrepod: DatabaseReference = database.getReference("LinkURLPrepod")
    private var studentPdfUrl = "https://drive.google.com/file/d/1vyobnZOsCxCNHi0lFjlEFijYl-EI2DoO/view?usp=sharing"
    private var teacherPdfUrl = "https://disk.yandex.ru/i/..."

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val studentButton: Button = binding.studentButton // кнопка для студента
        val teacherButton: Button = binding.teacherButton // кнопка для преподавателя

        myRefStudent.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<String>()
                if (value != null)
                {
                    studentPdfUrl = value
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
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })


        studentButton.setOnClickListener {
            openPartialCustomTab(requireContext(), studentPdfUrl)
        }

        teacherButton.setOnClickListener {
            openPartialCustomTab(requireContext(), teacherPdfUrl)
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
