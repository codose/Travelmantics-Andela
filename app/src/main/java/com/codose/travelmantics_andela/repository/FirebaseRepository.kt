package com.codose.travelmantics_andela.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseRepository {
    val auth = FirebaseAuth.getInstance()
    val database = Firebase.firestore
    val storage = Firebase.storage
}