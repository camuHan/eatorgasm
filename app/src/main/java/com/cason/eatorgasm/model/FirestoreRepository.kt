package com.cason.eatorgasm.model

import android.net.Uri
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import java.net.URI

interface FirestoreRepository {
    fun addUserIfNotExists(firebaseUser: FirebaseUser): Boolean?
    suspend fun fetchUserInfo(firebaseUser: FirebaseUser): UserInfoModel
    suspend fun updateUserToFirestore(userInfo: UserInfoModel): Boolean
    suspend fun uploadProfileImageInStorage(uri: Uri): String?
    suspend fun uploadProfileImage(data: Any): Boolean
    suspend fun fetchProfileImage(): DocumentSnapshot?
//    suspend fun fetchProfileImage(): Uri?


}