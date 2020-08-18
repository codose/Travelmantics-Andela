package com.codose.travelmantics_andela.views.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.codose.travelmantics_andela.R
import com.codose.travelmantics_andela.models.TravelMantic
import com.codose.travelmantics_andela.repository.MainViewModel
import com.codose.travelmantics_andela.utils.Resource
import com.codose.travelmantics_andela.utils.Utils.showToast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_new_travel.*

class NewTravelFragment : Fragment() {
    private lateinit var name : String
    private lateinit var description : String
    private lateinit var imageUrl : String
    private val viewModel by lazy{
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var travelMantic: TravelMantic

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_travel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = NewTravelFragmentArgs.fromBundle(requireArguments())
        image_select_btn.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1,1)
                .start(requireContext(), this)
        }

        if(args.travelmantic != null){
            add_new_text.text = "Update Location"
            save_btn.text = "Update"
            imageUrl = travelMantic.imageUrl
            travelMantic = args.travelmantic
            travel_name_text_input_edit.setText(travelMantic.placeName)
            travel_name_text_input_edit.setText(travelMantic.description)
            Glide.with(requireContext())
                .load(travelMantic.imageUrl)
                .into(new_travel_image)
            save_btn.setOnClickListener {
                updateTravel()
            }
        }else{
            imageUrl = ""
            save_btn.setOnClickListener {
                addNewTravel()
            }
        }
        setObservers()
    }

    private fun setObservers() {
        viewModel.travelMantic.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    val data = it.data
                    data.showToast(requireContext())
                    requireActivity().onBackPressed()
                    hideProgress()
                }
                is Resource.Failure -> {
                    it.message.showToast(requireContext())
                    hideProgress()
                }
            }
        })

        viewModel.uploadImage.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    val data = it.data
                    imageUrl = data
                    hideProgress()
                }
                is Resource.Failure -> {
                    it.message.showToast(requireContext())
                    hideProgress()
                }
            }
        })


    }

    private fun hideProgress() {

    }

    private fun showProgress() {

    }

    private fun updateTravel() {
        if(isValidated()){
            travelMantic.placeName = name
            travelMantic.description = description
            travelMantic.imageUrl = imageUrl
            viewModel.updateTravelMantic(travelMantic)
        }
    }

    private fun addNewTravel() {
        if (isValidated()){
            val travelMantic = TravelMantic()
            travelMantic.placeName = name
            travelMantic.description = description
            travelMantic.imageUrl = imageUrl
            viewModel.addTravelHistory(travelMantic)
        }
    }

    private fun isValidated(): Boolean {
        if(travel_name_text_input_edit.text.isNullOrEmpty()){
            travel_name_text_input_layout.error = "Valid Name Required"
            return false
        }else{
            travel_name_text_input_layout.error = ""
            name = travel_name_text_input_edit.text.toString()
        }
        if(travel_description_text_input_edit.text.isNullOrEmpty()){
            travel_description_text_input_layout.error = "Password id required"
            return false
        }else{
            travel_description_text_input_layout.error = ""
            description = travel_description_text_input_edit.text.toString()
        }
        if(imageUrl == ""){
            "Image Required".showToast(requireContext())
            return false
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                Glide.with(this)
                    .load(result.uri)
                    .placeholder(R.drawable.ic_user)
                    .into(new_travel_image)
                viewModel.uploadImage(result.uri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                error.message!!.showToast(requireContext())
            }
        }
    }
}