package com.cason.eatorgasm.modelimpl

import android.net.Uri
import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.util.CMLog.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await
import java.net.URI
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor() : FirestoreRepository {
    private val mRepoJob = Job()
    protected val mAsyncScope = CoroutineScope(Dispatchers.Default + mRepoJob)

    override fun addUserIfNotExists(firebaseUser: FirebaseUser): Boolean {
        var result = false
        val db = FirebaseFirestore.getInstance()

        val task = db.collection(COLLECTION_NAME_USERS).document(firebaseUser.uid).get()
        if(task.isSuccessful && task.result.exists()) {
            d(TAG, "uid is already exists. : " + firebaseUser.uid)
        } else {
            d(TAG, "there is no uid. need to add data")
            addUserToFirestore(firebaseUser, db)
            result = true
        }

        return result
    }

    private fun addUserToFirestore(firebaseUser: FirebaseUser, db: FirebaseFirestore) {
        val mUserProfile = UserInfoModel()

        mUserProfile.userId = firebaseUser.uid
        mUserProfile.name = firebaseUser.displayName
        mUserProfile.email = firebaseUser.email
        val photoUrl = if (firebaseUser.photoUrl == null) "" else firebaseUser.photoUrl.toString()
        mUserProfile.photoUrl = photoUrl
        mUserProfile.timestapCreated = FieldValue.serverTimestamp().toString()

        db.collection(COLLECTION_NAME_USERS)
            .document(firebaseUser.uid)
            .set(mUserProfile)
            .addOnSuccessListener {
                CMLog.d(TAG, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e -> CMLog.w(TAG, "Error writing document \n$e") }
    }

    override suspend fun fetchUserInfo(firebaseUser: FirebaseUser): UserInfoModel {
        val model = UserInfoModel()
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_USERS).document(firebaseUser.uid).get().addOnCompleteListener {
            if(!it.isSuccessful) {
                CMLog.e("HSH", "fail in \n + ${it.exception}")
            } else {
                CMLog.e("HSH", "success in")
                model.userId = it.result.getString("userId")
                model.name = it.result.getString("strName")
                model.email = it.result.getString("email")
                model.phoneNumber = it.result.getString("phoneNumber")
                model.photoUrl = it.result.getString("photoUrl")
            }
        }.await()

        return model
    }

    override suspend fun updateUserToFirestore(userInfo: UserInfoModel): Boolean {
        var result = false
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_USERS)
            .document(userInfo.userId.toString())
            .set(userInfo).addOnCompleteListener {
                if(it.isSuccessful) {
                    result = true
                } else {
                    CMLog.e(TAG, "fail in \n + ${it.exception}")
                }
            }.await()
        return result
    }

    override suspend fun updateProfileImageToFirestore(uri: Uri): Boolean {
        var result = false
        val storageRef = FirebaseStorage.getInstance().reference
        val user = FirebaseAuth.getInstance().currentUser
        val imageRef = storageRef.child("EatUser/${user?.uid}/photo")
        imageRef.putFile(uri).addOnCompleteListener{
            if(it.isSuccessful) {
                result = true
            } else {
                CMLog.e(TAG, "fail in \n + ${it.exception}")
            }
        }.await()
        return result
    }

    companion object {
        private val TAG = FirestoreRepositoryImpl::class.java.simpleName
        private const val COLLECTION_NAME_USERS = "users"
    }
}