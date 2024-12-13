package com.example.asuapp001.ui.AdminPanel

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.asuapp001.databinding.FragmentAdmnimPanelBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AdmnimPanel : Fragment() {

    private var _binding: FragmentAdmnimPanelBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseDatabase

    lateinit var authTextBD : TextView
    lateinit var LoginBtnBD : Button
    lateinit var LoginPassDB : EditText
    lateinit var LoginEmailDB : EditText
    lateinit var adChange : TextView
    lateinit var adTextChange : EditText
    lateinit var adUrlImageChange : TextView
    lateinit var adBtnChange : Button
    lateinit var adURLChange : TextView
    lateinit var switchImgVisibleDB : Switch

    companion object {
        fun newInstance() = AdmnimPanel()
    }

    private val viewModel: AdmnimPanelViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        val AdmnimPanelViewModel = ViewModelProvider(this).get(AdmnimPanelViewModel::class.java)
        _binding = FragmentAdmnimPanelBinding.inflate(inflater, container, false)
        val  root: View = binding.root

        auth = Firebase.auth
        database = Firebase.database
        val myRef = database.getReference("message")
        val myRefImg = database.getReference("ImageUrl")
        val myRefBool = database.getReference("VisibleImage")

        authTextBD = binding.AuthTextBD
        LoginBtnBD = binding.LoginButtonBD
        LoginPassDB = binding.LoginPassword
        LoginEmailDB = binding.LoginEmailAddress
        adChange = binding.adChange
        adTextChange = binding.adTextChange
        adBtnChange = binding.adBtnChange
        adUrlImageChange = binding.adURLImageChange
        adURLChange = binding.adUrlChange
        switchImgVisibleDB = binding.switchImgVisibleDB


        LoginBtnBD.setOnClickListener {
            if (LoginPassDB.text.toString() != "" || LoginPassDB.text.toString() != "")
            {
                signIn(LoginEmailDB.text.toString(), LoginPassDB.text.toString())
            }
            else
            {
                LoginPassDB.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                LoginEmailDB.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                LoginEmailDB.setHintTextColor(Color.RED)
                LoginPassDB.setHintTextColor(Color.RED)
                Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }

        LoginPassDB.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                LoginPassDB.setHintTextColor(Color.GRAY)
                LoginPassDB.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                LoginPassDB.setHintTextColor(Color.GRAY)
                LoginPassDB.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            }

            override fun afterTextChanged(s: Editable?) {
                LoginPassDB.setHintTextColor(Color.GRAY)
                LoginPassDB.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            }

        })

        LoginEmailDB.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                LoginEmailDB.setHintTextColor(Color.GRAY)
                LoginEmailDB.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                LoginEmailDB.setHintTextColor(Color.GRAY)
                LoginEmailDB.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            }

            override fun afterTextChanged(s: Editable?) {
                LoginEmailDB.setHintTextColor(Color.GRAY)
                LoginEmailDB.background.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
            }

        })



        adBtnChange.setOnClickListener {
            myRef.setValue(adTextChange.text.toString())
            myRefImg.setValue(adUrlImageChange.text.toString())

        }


        switchImgVisibleDB.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
            {
                myRefBool.setValue(true)
                Toast.makeText(activity, "Изоброжение есть", Toast.LENGTH_SHORT).show()
            }
            else
            {
                myRefBool.setValue(false)
                Toast.makeText(activity, "Изоброжения нет", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Firebase.auth.signOut()
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(activity,"Вход Выполнен",Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    updateUI(user)
                }
                else
                {
                    Toast.makeText(activity, "Ошибка", Toast.LENGTH_SHORT).show()
                    LoginPassDB.hint = "Неверный пароль"
                    LoginEmailDB.hint = "Неверный email"
                    LoginEmailDB.text = null
                    LoginPassDB.text = null
                    LoginPassDB.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                    LoginEmailDB.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
                    LoginEmailDB.setHintTextColor(Color.RED)
                    LoginPassDB.setHintTextColor(Color.RED)
                }
            }
    }


    private fun updateUI(user: FirebaseUser?)
    {
        authTextBD.visibility = View.GONE
        LoginBtnBD.visibility = View.GONE
        LoginPassDB.visibility = View.GONE
        LoginEmailDB.visibility = View.GONE
        adChange.visibility = View.VISIBLE
        adTextChange.visibility = View.VISIBLE
        adBtnChange.visibility = View.VISIBLE
        adUrlImageChange.visibility = View.VISIBLE
        adURLChange.visibility = View.VISIBLE
        switchImgVisibleDB.visibility = View.VISIBLE

    }


}


