package com.example.asuapp001.ui.diaryPrilagusha

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.asuapp001.databinding.FragmentSlideshowBinding

class diaryPrilagushaFragment : Fragment() {
    //
    //
    // НИКОЛАЙ ВСЁ ЧТО Я ПОМЕТИЛ РУССКИМИ КОМЕНТАРИЯМИ ЭТО Я НАПИСАЛ А ЧТО НЕ ПОМЕТИЛ ЗДЕСЬ И ТАК БЫЛО ПО ШАБЛОНУ НЕ ТРОГАЙ ОПАСНО!!!
    // всё названия id в fragment_slideshow.xml
    //
    private var _binding: FragmentSlideshowBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var EditTextM : EditText // переменная для измнения этой как его назвать записки
    lateinit var EditTextT : EditText
    lateinit var EditTextW : EditText
    lateinit var EditTextTh : EditText
    lateinit var EditTextF : EditText
    lateinit var EditTextS : EditText


    lateinit var Pref : SharedPreferences // эта переменная для создания таблицы где будет хранится текст

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle? ): View
    {
        val diaryPrilagushaViewModel = ViewModelProvider(this).get(diaryPrilagushaViewModel::class.java)
        Pref = requireActivity()!!.applicationContext.getSharedPreferences("Table",   Context.MODE_PRIVATE) // здесь присваевается сама табла ну
                                                                                                                  // первое значение это имя а
                                                                                                                  // второе я хз что это ну это и не важно потому что создовать присваеть снова ничего не надо
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        EditTextM = binding.editTextMonday // тут для можно сказать пример как присвоеить idшник
        EditTextM.setText("0") // это тип я хотел иницилизацию типа сделать
        EditTextM.setText(Pref?.getString("EditTextM", "Напишите что нибудь!!!")!!.toString()) // здесь впихиватся значения из нашей таблицы в которой мы сохроняем вонючий текст первое значение в методе это ключ потом поймешь зачем а второе это значение по умолчанию

        EditTextT = binding.editTextTuesday
        EditTextT.setText("0")
        EditTextT.setText(Pref?.getString("EditTextT", "")!!.toString())

        EditTextW = binding.editTextWednesday
        EditTextW.setText("0")
        EditTextW.setText(Pref?.getString("EditTextW", "")!!.toString())

        EditTextTh = binding.editTextThursday
        EditTextTh.setText("0")
        EditTextTh.setText(Pref?.getString("EditTextTh", "")!!.toString())

        EditTextF = binding.editTextFriday
        EditTextF.setText("0")
        EditTextF.setText(Pref?.getString("EditTextF", "")!!.toString())

        EditTextS = binding.editTextSaturday
        EditTextS.setText("0")
        EditTextS.setText(Pref?.getString("EditTextS", "")!!.toString())

        EditTextM.addTextChangedListener(object : TextWatcher { // Методы ака слушатеель
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Savedata(EditTextM.text.toString(), "EditTextM")
                // Выполняется перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Выполняется во время изменения текста
                Savedata(EditTextM.text.toString(), "EditTextM")
            }


            override fun afterTextChanged(s: Editable?) {
                // Выполняется после изменения текста
                Savedata(EditTextM.text.toString(), "EditTextM")
            }
        })
        EditTextT.addTextChangedListener(object : TextWatcher { // Методы ака слушатеель
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Savedata(EditTextT.text.toString(), "EditTextT")
                // Выполняется перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Выполняется во время изменения текста
                Savedata(EditTextT.text.toString(), "EditTextT")
            }


            override fun afterTextChanged(s: Editable?) {
                // Выполняется после изменения текста
                Savedata(EditTextT.text.toString(), "EditTextT")
            }
        })
        EditTextW.addTextChangedListener(object : TextWatcher { // Методы ака слушатеель
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Savedata(EditTextW.text.toString(), "EditTextW")
                // Выполняется перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Выполняется во время изменения текста
                Savedata(EditTextW.text.toString(), "EditTextW")
            }


            override fun afterTextChanged(s: Editable?) {
                // Выполняется после изменения текста
                Savedata(EditTextW.text.toString(), "EditTextW")
            }
        })
        EditTextTh.addTextChangedListener(object : TextWatcher { // Методы ака слушатеель
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Savedata(EditTextTh.text.toString(), "EditTextTh")
                // Выполняется перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Выполняется во время изменения текста
                Savedata(EditTextTh.text.toString(), "EditTextTh")
            }


            override fun afterTextChanged(s: Editable?) {
                // Выполняется после изменения текста
                Savedata(EditTextTh.text.toString(), "EditTextTh")
            }
        })
        EditTextF.addTextChangedListener(object : TextWatcher { // Методы ака слушатеель
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Savedata(EditTextF.text.toString(), "EditTextF")
                // Выполняется перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Выполняется во время изменения текста
                Savedata(EditTextF.text.toString(), "EditTextF")
            }


            override fun afterTextChanged(s: Editable?) {
                // Выполняется после изменения текста
                Savedata(EditTextF.text.toString(), "EditTextF")
            }
        })
        EditTextS.addTextChangedListener(object : TextWatcher { // Методы ака слушатеель
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Savedata(EditTextS.text.toString(), "EditTextS")
                // Выполняется перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Выполняется во время изменения текста
                Savedata(EditTextS.text.toString(), "EditTextS")
            }


            override fun afterTextChanged(s: Editable?) {
                // Выполняется после изменения текста
                Savedata(EditTextS.text.toString(), "EditTextS")
            }
        })

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        Savedata(EditTextM.text.toString(), "EditTextM");
        Savedata(EditTextT.text.toString(), "EditTextT");
        Savedata(EditTextW.text.toString(), "EditTextW");
        Savedata(EditTextTh.text.toString(), "EditTextTh");
        Savedata(EditTextF.text.toString(), "EditTextF");
        Savedata(EditTextS.text.toString(), "EditTextS");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Savedata(EditTextM.text.toString(), "EditTextM");
        Savedata(EditTextT.text.toString(), "EditTextT");
        Savedata(EditTextW.text.toString(), "EditTextW");
        Savedata(EditTextTh.text.toString(), "EditTextTh");
        Savedata(EditTextF.text.toString(), "EditTextF");
        Savedata(EditTextS.text.toString(), "EditTextS");
    }


    fun Savedata(data : String, key : String) // ну по названию и так понятно
    {
       val editor = Pref?.edit() // это едитор ну понятно
        editor?.putString(key, data) // первое значение в методе это ключ а второе значение в методе это значение которое мы сохроняем
        editor?.apply() // ну я как понял это сохронить
    }



}