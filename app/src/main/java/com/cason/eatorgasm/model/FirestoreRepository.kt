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

    // upload image
    suspend fun uploadProfileImageInStorage(uri: Uri): String?
    suspend fun uploadImageInStorage(storageName: String, uri: String): String?
    suspend fun uploadImageListInStorage(storageName: String
                                         , imageList: ArrayList<String>?): ArrayList<String>?
    suspend fun uploadProfileImage(data: Any): Boolean

    // fetch image
    suspend fun fetchProfileImage(): DocumentSnapshot?
    suspend fun fetchProfileImageByUid(uid: String): DocumentSnapshot?

    // set board
    suspend fun setFireStoreData(collectionName: String, data: Any): Boolean
    suspend fun setFireStoreDataByDocumentId(collectionName: String, data: Any, documentId: String): Boolean

    // delete board
    suspend fun deleteBoardByBoardId(boardId: String?): Boolean

    // get board
    suspend fun fetchBoard(boardId: String): DocumentSnapshot?
    suspend fun fetchBoardList(): List<DocumentSnapshot>?
//    suspend fun fetchProfileImage(): Uri?

    // set subdata
    suspend fun setFireStoreSubDataByDocumentId(collectionName: String, documentId: String
                                                , subCollectionName: String, subDocumentId: String, data: Any): Boolean

    // get subdata List
    suspend fun fetchFireStoreSubDataList(collectionName: String, documentId: String,
                                          subCollectionName: String): List<DocumentSnapshot>?


}