package com.codose.travelmantics_andela.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codose.travelmantics_andela.models.TravelMantic
import com.codose.travelmantics_andela.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel(){
    private val repository = FirebaseRepository()
    val login = MutableLiveData<Resource<String>>()
    val travelMantic = MutableLiveData<Resource<String>>()
    val allTravels = MutableLiveData<Resource<ArrayList<TravelMantic>>>()



    fun loginUser(email : String, password : String){
        login.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.loginUser(email, password)
                login.postValue(data)
            }
        }
    }

    fun registerUser(email : String, password : String, fullname : String){
        login.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.registerUser(email, password, fullname)
                login.postValue(data)
            }
        }
    }

    fun addTravelHistory(travel : TravelMantic){
        travelMantic.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.addTravelmantic(travel)
                travelMantic.postValue(data)
            }
        }
    }

    fun updateTravelMantic(travel : TravelMantic){
        travelMantic.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.updateTravelMantics(travel)
                travelMantic.postValue(data)
            }
        }
    }

    fun getTravelmantics(){
        allTravels.value = Resource.Loading()
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                val data = repository.getTravelmantics()
                allTravels.postValue(data)
            }
        }
    }


}