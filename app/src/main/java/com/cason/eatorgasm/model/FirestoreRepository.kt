package com.cason.eatorgasm.model

import android.net.Uri
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.google.firebase.auth.FirebaseUser
import java.net.URI

interface FirestoreRepository {
    fun addUserIfNotExists(firebaseUser: FirebaseUser): Boolean?
    suspend fun fetchUserInfo(firebaseUser: FirebaseUser): UserInfoModel
    suspend fun updateUserToFirestore(userInfo: UserInfoModel): Boolean
    suspend fun updateProfileImage(uri: Uri): Boolean
    suspend fun fetchProfileImage(): Uri?
}