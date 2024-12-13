package com.example.asuapp001.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.asuapp001.databinding.FragmentGalleryBinding
import android.content.Intent
import android.net.Uri

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

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

        // Ссылки на PDF файлы на Яндекс Диске
        val studentPdfUrl = "https://disk.yandex.ru/i/..." // Замените на ссылку для студента
        val teacherPdfUrl = "https://disk.yandex.ru/i/..." // Замените на ссылку для преподавателя

        studentButton.setOnClickListener {
            openPdfFromUrl(studentPdfUrl)
        }

        teacherButton.setOnClickListener {
            openPdfFromUrl(teacherPdfUrl)
        }

        return root
    }

    private fun openPdfFromUrl(pdfUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(pdfUrl)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
