package com.codose.travelmantics_andela.repository

import com.codose.travelmantics_andela.models.TravelMantic
import com.codose.travelmantics_andela.models.User
import com.codose.travelmantics_andela.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.lang.Exception

/*
    Created by: Osemwingie Oshodin
    Date: 18th August, 2020
 */
class FirebaseRepository {
    //This is where all Firebase calls are made
    val auth = FirebaseAuth.getInstance()
    val database = Firebase.firestore
    val storage = Firebase.storage

    suspend fun registerUser(email : String, password : String, fullName : String) : Resource<String> {
        return try{
            val result =auth.createUserWithEmailAndPassword(email,password).await()
            val user = User(auth.currentUser!!.uid,fullName,email)
            saveUser(user)
            Resource.Success("Registration Successful")
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun saveUser(user : User) : Resource<String>{
        return try{
            val result = database.collection("Travelmantic").document(auth.currentUser!!.uid).collection("Profile").document(auth.currentUser!!.uid).set(user)
            Resource.Success("Update Successful")
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun addTravelmantic(travel : TravelMantic) : Resource<String>{
        return try{
            val id = database.collection("Travelmantic").document(auth.currentUser!!.uid).collection("Travel Details").document().id
            travel.id = id
            database.collection("Travelmantic").document(auth.currentUser!!.uid).collection("Travel Details").document(id).set(travel)
            Resource.Success("Update Successful")
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun updateTravelMantics(travel : TravelMantic) : Resource<String>{
        return try{
            database.collection("Travelmantic").document(auth.currentUser!!.uid).collection("Travel Details").document(travel.id).set(travel)
            Resource.Success("Update Successful")
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun getTravelmantics() : Resource<ArrayList<TravelMantic>>{
        return try{
            val data = database.collection("Travelmantic").document(auth.currentUser!!.uid).collection("Travel Details").get().await()
            val travels = data.toObjects<TravelMantic>()
            Resource.Success(travels as ArrayList)
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }

    suspend fun loginUser(email: String, password: String) : Resource<String>{
        return try{
            auth.signInWithEmailAndPassword(email,password).await()
            Resource.Success("LogIn Successful")
        }catch (e : Exception){
            Resource.Failure(e.message!!)
        }
    }
}