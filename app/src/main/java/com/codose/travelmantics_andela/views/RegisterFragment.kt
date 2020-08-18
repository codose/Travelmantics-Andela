package com.codose.travelmantics_andela.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codose.travelmantics_andela.R
import com.codose.travelmantics_andela.repository.MainViewModel
import com.codose.travelmantics_andela.utils.Resource
import com.codose.travelmantics_andela.utils.Utils.showToast
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var email : String
    private lateinit var password : String
    private lateinit var fullName : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register_button.setOnClickListener { 
            registerUser()
        }
        setObservers()
    }

    private fun registerUser() {
        if(register_fullname_text_input_edit.text.isNullOrEmpty()){
            register_fullname_text_input_layout.error = "Full Name Required"
            return
        }else{
            register_fullname_text_input_layout.error = ""
            fullName = register_fullname_text_input_edit.text.toString()
        }
        if(register_email_text_input_edit.text.isNullOrEmpty()){
            register_email_text_input_layout.error = "Valid Email Required"
            return
        }else{
            register_email_text_input_layout.error = ""
            email = register_email_text_input_edit.text.toString()
        }
        if(register_pass_text_input_edit.text.isNullOrEmpty()){
            register_pass_text_input_layout.error = "Valid Password is required"
            return
        }else{
            if(register_pass_text_input_edit.text.toString().length >= 8){
                register_pass_text_input_layout.error = "Valid Password is required"
                return
            }else{
                register_pass_text_input_layout.error = ""
                password = register_pass_text_input_edit.text.toString()
            }
        }
        if(register_confirm_pass_text_input_edit.text.isNullOrEmpty()){
            register_confirm_pass_text_input_layout.error = "Valid Password is required"
            return
        }else{
            if(register_confirm_pass_text_input_edit.text.toString() == register_pass_text_input_edit.text.toString()){
                register_confirm_pass_text_input_layout.error = "Passwords does not match"
                return
            }else{
                register_confirm_pass_text_input_layout.error = ""
            }
        }
        viewModel.registerUser(email,password,fullName)
    }

    private fun setObservers() {
        viewModel.login.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    val data = it.data
                    hideProgress()
                    data.showToast(requireContext())
                }
                is Resource.Failure -> {
                    hideProgress()
                }
            }
        })
    }

    private fun hideProgress() {
        register_progress.visibility = View.GONE
    }

    private fun showProgress() {
        register_progress.visibility = View.VISIBLE
    }

}