package com.codose.travelmantics_andela.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codose.travelmantics_andela.R
import com.codose.travelmantics_andela.repository.MainViewModel
import com.codose.travelmantics_andela.utils.Resource
import com.codose.travelmantics_andela.utils.Utils
import com.codose.travelmantics_andela.utils.Utils.showToast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var email : String
    private lateinit var password : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(FirebaseAuth.getInstance().currentUser != null){
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
        }
        login_button.setOnClickListener {
            loginUser()
        }
        login_register_btn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        setObservers()
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
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
                    data.showToast(requireContext())
                }
                is Resource.Failure -> {
                    hideProgress()
                }
            }
        })
    }

    private fun hideProgress() {
        login_progress.visibility = GONE
    }

    private fun showProgress() {
        login_progress.visibility = VISIBLE
    }

    private fun loginUser() {
        if(login_email_text_input_edit.text.isNullOrEmpty()){
            login_email_text_input_layout.error = "Valid Email Required"
            return
        }else{
            login_email_text_input_layout.error = ""
            email = login_email_text_input_edit.text.toString()
        }
        if(login_pass_text_input_edit.text.isNullOrEmpty()){
            login_pass_text_input_layout.error = "Password id required"
            return
        }else{
            login_pass_text_input_layout.error = ""
            password = login_pass_text_input_edit.text.toString()
        }
        viewModel.loginUser(email, password)
    }

}