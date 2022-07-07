package com.cason.eatorgasm.model

import android.net.Uri
import com.cason.eatorgasm.define.CMEnum
import com.cason.eatorgasm.define.EatDefine.FireBaseStorage.FIREBASE_STORAGE_PROFILE_IMAGES
import com.cason.eatorgasm.define.EatDefine.FireStoreCollection.COLLECTION_NAME_BOARDS
import com.cason.eatorgasm.define.EatDefine.FireStoreCollection.COLLECTION_NAME_PROFILE_IMAGES
import com.cason.eatorgasm.define.EatDefine.FireStoreCollection.COLLECTION_NAME_USERS
import com.cason.eatorgasm.model.entity.UserInfoModel
import com.cason.eatorgasm.util.CMLog
import com.cason.eatorgasm.util.CMLog.d
import com.cason.eatorgasm.util.ProgressManager
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storageMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor() : FirestoreRepository {
    private val mRepoJob = Job()
    protected val mAsyncScope = CoroutineScope(Dispatchers.Default + mRepoJob)

    override suspend fun addUserIfNotExists(firebaseUser: FirebaseUser): Boolean {
        var result = false
        val db = FirebaseFirestore.getInstance()

        db.collection(COLLECTION_NAME_USERS).document(firebaseUser.uid).get()
            .addOnCompleteListener {
            if(it.isSuccessful && it.result.exists()) {
                d(TAG, "uid is already exists. : " + firebaseUser.uid)
            } else {
                d(TAG, "there is no uid. need to add data")
                addUserToFirestore(firebaseUser, db)
                result = true
            }
        }.await()

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
        db.collection(COLLECTION_NAME_USERS).document(firebaseUser.uid).get()
            .addOnCompleteListener {
            if(!it.isSuccessful) {
                CMLog.e("HSH", "fail in \n + ${it.exception}")
            } else {
                CMLog.e("HSH", "success in")
                model.userId = it.result.getString("userId")
                model.name = it.result.getString("name")
                model.email = it.result.getString("email")
                model.phoneNumber = it.result.getString("phoneNumber")
                model.photoUrl = it.result.getString("photoUrl")
            }
        }.await()

        return model
    }

    override suspend fun fetchCurrentUserInfo(): UserInfoModel? {
        val uid = FirebaseAuth.getInstance().uid ?: return null

        val model = UserInfoModel()
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_USERS).document(uid).get()
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    CMLog.e("HSH", "fail in \n + ${it.exception}")
                } else {
                    CMLog.e("HSH", "success in")
                    model.userId = it.result.getString("userId")
                    model.name = it.result.getString("name")
                    model.email = it.result.getString("email")
                    model.phoneNumber = it.result.getString("phoneNumber")
                    model.photoUrl = it.result.getString("photoUrl")
                }
            }.await()

        return model
    }

    override suspend fun fetchUserInfoByUid(uid: String): UserInfoModel {
        val model = UserInfoModel()
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_USERS).document(uid).get()
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    CMLog.e("HSH", "fail in \n + ${it.exception}")
                } else {
                    CMLog.e("HSH", "success in")
                    model.userId = it.result.getString("userId")
                    model.name = it.result.getString("name")
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

    override suspend fun updateProfile(userInfo: UserInfoModel): Boolean {
        var result = false
        val user = Firebase.auth.currentUser
        val profileUpdates = userProfileChangeRequest {
            displayName = userInfo.name
            photoUri = Uri.parse(userInfo.photoUrl)
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    CMLog.d(TAG, "User profile updated.")
                    result = true
                }
            }
        return result
    }

    override suspend fun uploadProfileImageInStorage(uri: Uri): String? {
        var result: String? = null
        val storageRef = FirebaseStorage.getInstance().reference
        val user = FirebaseAuth.getInstance().currentUser
        val imageRef = storageRef.child(FIREBASE_STORAGE_PROFILE_IMAGES).child(user?.uid!!)

        ProgressManager.setMaxValue(100)
        ProgressManager.showProgressBar(CMEnum.CommonProgressType.UPLOAD, null)

        imageRef.putFile(uri).addOnProgressListener {
            val transferredByte = it.bytesTransferred
            val totalByte = it.totalByteCount
            val progress: Double = 100.0 * transferredByte / totalByte

            ProgressManager.updateByPercent(progress.toInt())

        }.continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener{
            if(it.isSuccessful) {
                result= it.result.toString()
            } else {
                CMLog.e(TAG, "fail in \n + ${it.exception}")
            }
            ProgressManager.dismissProgressBar()
        }.await()
        return result
    }

    override suspend fun uploadImageInStorage(storageName: String, uri: String): String? {
        if (uri == "") {
            return null
        }

        var result: String? = null
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child(storageName)

        imageRef.putFile(Uri.parse(uri)).continueWithTask { task: Task<UploadTask.TaskSnapshot> ->
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener{
            if(it.isSuccessful) {
                result= it.result.toString()
            } else {
                CMLog.e(TAG, "fail in \n + ${it.exception}")
            }
        }.await()
        return result
    }

    override suspend fun uploadImageListInStorage(storageName: String
                                                  , imageList: ArrayList<String>?): ArrayList<String>? {
        if(imageList == null) {
            return null
        }

        val resultList = ArrayList<String>()
        val storageRef = FirebaseStorage.getInstance().reference
        val imageStorageRef = storageRef.child(storageName)

        imageList.forEachIndexed { index, uri ->
            if(uri.contains(FIRESTORE_DOWNLOAD_URL)) {
                resultList.add(uri)
                return@forEachIndexed
            }

            val metadata = storageMetadata {
                setCustomMetadata("index", "" + index)
            }

            val lastIndex: Int = uri.lastIndexOf('/')
            if (lastIndex < 0) {
                return@forEachIndexed
            }

            val fileName: String = uri.substring(lastIndex + 1)
            val imageRef = imageStorageRef.child(fileName)

            imageRef.putFile(Uri.parse(uri)).continueWithTask {
                return@continueWithTask imageRef.downloadUrl
            }.addOnCompleteListener{
                if(it.isSuccessful) {
                    resultList.add(it.result.toString())
                } else {
                    CMLog.e(TAG, "fail in \n + ${it.exception}")
                }
            }.await()
        }
        return resultList
    }

    /* not used */
    override suspend fun uploadProfileImage(data: Any): Boolean {
        var result = false
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_PROFILE_IMAGES)
            .document(uid!!)
            .set(data).addOnCompleteListener {
                if(it.isSuccessful) {
                    result = true
                } else {
                    CMLog.e(TAG, "fail in \n + ${it.exception}")
                }
            }.await()
        return result
    }

    /* not used */
    override suspend fun fetchProfileImage(): DocumentSnapshot?{
        var snapshot:DocumentSnapshot? = null
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_PROFILE_IMAGES).document(uid!!).get()
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    CMLog.e("HSH", "fail in \n + ${it.exception}")
                } else {
                    CMLog.e("HSH", "success in")
                    snapshot = it.result
                }
            }.await()

        return snapshot
    }

    override suspend fun fetchProfileImageByUid(uid: String): DocumentSnapshot?{
        var snapshot:DocumentSnapshot? = null
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_PROFILE_IMAGES).document(uid).get()
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    CMLog.e("HSH", "fail in \n + ${it.exception}")
                } else {
                    CMLog.e("HSH", "success in")
                    snapshot = it.result
                }
            }.await()

        return snapshot
    }

    override suspend fun setFireStoreData(collectionName: String, data: Any): Boolean {
        var result = false
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        if(uid != null) {
            db.collection(collectionName)
                .document()
                .set(data).addOnCompleteListener {
                    if (it.isSuccessful) {
                        result = true
                    } else {
                        CMLog.e(TAG, "fail in \n + ${it.exception}")
                    }
                }.await()
        }
        return result
    }

    override suspend fun setFireStoreDataByDocumentId(collectionName: String, data: Any, documentId: String): Boolean {
        var result = false
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        if(uid != null) {
            db.collection(collectionName)
                .document(documentId)
                .set(data).addOnCompleteListener {
                    if (it.isSuccessful) {
                        result = true
                    } else {
                        CMLog.e(TAG, "fail in \n + ${it.exception}")
                    }
                }.await()
        }
        return result
    }

    override suspend fun deleteBoardByBoardId(boardId: String?): Boolean {
        if(boardId == null) {
            return false
        }

        var result = false
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        if(uid != null) {
            db.collection(COLLECTION_NAME_BOARDS)
                .document(boardId).delete()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        result = true
                    } else {
                        CMLog.e(TAG, "fail in \n + ${it.exception}")
                    }
                }.await()
        }
        return result
    }

    override suspend fun fetchBoard(boardId: String): DocumentSnapshot? {
        var docSnapshot: DocumentSnapshot? = null
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_BOARDS).document(boardId).get()
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    CMLog.e("HSH", "fail in \n + ${it.exception}")
                } else {
                    CMLog.e("HSH", "success in")
                    docSnapshot = it.result
                }
            }.await()

        return docSnapshot
    }

    override suspend fun fetchBoardList(): List<DocumentSnapshot>? {
        var list: List<DocumentSnapshot>? = null
        val db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION_NAME_BOARDS).get()
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    CMLog.e("HSH", "fail in \n + ${it.exception}")
                } else {
                    CMLog.e("HSH", "success in")
                    list = it.result.documents
                }
            }.await()

        return list
    }

    override suspend fun setFireStoreSubDataByDocumentId(collectionName: String, documentId: String,
                                                         subCollectionName: String, subDocumentId: String, data: Any): Boolean {
        var result = false
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        if(uid != null) {
            db.collection(collectionName)
                .document(documentId).collection(subCollectionName).document(subDocumentId)
                .set(data).addOnCompleteListener {
                    if (it.isSuccessful) {
                        result = true
                    } else {
                        CMLog.e(TAG, "fail in \n + ${it.exception}")
                    }
                }.await()
        }
        return result
    }

    override suspend fun fetchFireStoreSubDataList(collectionName: String, documentId: String,
                                                   subCollectionName: String): List<DocumentSnapshot>? {
        var list: List<DocumentSnapshot>? = null
        val db = FirebaseFirestore.getInstance()
        db.collection(collectionName).document(documentId)
            .collection(subCollectionName).get()
            .addOnCompleteListener {
                if(!it.isSuccessful) {
                    CMLog.e("HSH", "fail in \n + ${it.exception}")
                } else {
                    CMLog.e("HSH", "success in")
                    list = it.result.documents
                }
            }.await()

        return list
    }

    companion object {
        private val TAG = FirestoreRepositoryImpl::class.java.simpleName
        private val FIRESTORE_DOWNLOAD_URL = "https://firebasestorage.googleapis.com"
    }
}