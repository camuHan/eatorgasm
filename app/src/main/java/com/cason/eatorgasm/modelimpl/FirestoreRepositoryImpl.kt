package com.cason.eatorgasm.modelimpl

import com.cason.eatorgasm.model.FirestoreRepository
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.util.CMLog.d
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await
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

    companion object {
        private val TAG = FirestoreRepositoryImpl::class.java.simpleName
        private const val COLLECTION_NAME_USERS = "users"
    }

//    override suspend fun addUserIfNotExists(firebaseUser: FirebaseUser?): Boolean? {
//        TODO("Not yet implemented")
//    }
}