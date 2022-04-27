package com.cason.eatorgasm.model

import com.cason.eatorgasm.model.entity.UserInfoModel
import com.google.firebase.auth.FirebaseUser

interface FirestoreRepository {
    fun addUserIfNotExists(firebaseUser: FirebaseUser): Boolean?
    suspend fun fetchUserInfo(firebaseUser: FirebaseUser): UserInfoModel
}