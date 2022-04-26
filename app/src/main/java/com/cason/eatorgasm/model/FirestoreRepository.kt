package com.cason.eatorgasm.model

import com.cason.eatorgasm.model.entity.EatUserProfileItem
import com.google.firebase.auth.FirebaseUser

interface FirestoreRepository {
    fun addUserIfNotExists(firebaseUser: FirebaseUser): Boolean?
    fun fetchUserInfo(firebaseUser: FirebaseUser): EatUserProfileItem
}