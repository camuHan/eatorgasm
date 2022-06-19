package com.cason.eatorgasm.model

import android.net.Uri
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import java.net.URI

interface FirestoreRepository {
    // log in
    suspend fun addUserIfNotExists(firebaseUser: FirebaseUser): Boolean?

    // user info
    suspend fun fetchUserInfo(firebaseUser: FirebaseUser): UserInfoModel
    suspend fun fetchCurrentUserInfo(): UserInfoModel?
    suspend fun fetchUserInfoByUid(uid: String): UserInfoModel
    suspend fun updateUserToFirestore(userInfo: UserInfoModel): Boolean

    // update profile
    suspend fun updateProfile(userInfo: UserInfoModel): Boolean

    // profile image
    suspend fun uploadProfileImageInStorage(uri: Uri): String?
    suspend fun uploadProfileImage(data: Any): Boolean
    suspend fun fetchProfileImage(): DocumentSnapshot?
    suspend fun fetchProfileImageByUid(uid: String): DocumentSnapshot?

    // set board
    suspend fun modifyBoardByBoardId(data: Any, boardId: String?): Boolean
    suspend fun uploadBoard(data: Any): Boolean

    // delete board
    suspend fun deleteBoardByBoardId(boardId: String?): Boolean

    // get board
    suspend fun fetchBoard(boardId: String): DocumentSnapshot?
    suspend fun fetchBoardList(): List<DocumentSnapshot>?
//    suspend fun fetchProfileImage(): Uri?


}