package com.cason.eatorgasm.model.entity

import com.cason.eatorgasm.R
import com.google.firebase.auth.FirebaseUser

data class UserInfoModel(
    var userId: String? = "",
    var name: String? = "",
    var email: String? = "",
    var password: String ?= "",
    var phoneNumber: String? = "",
    var photoUrl: String? = "",
    var timestapCreated: String? = ""
) {
    fun setFirebaseUserData (user: FirebaseUser): UserInfoModel {
        userId = user.uid
        name = user.displayName
        email = user.email
        phoneNumber = user.phoneNumber
        photoUrl = user.photoUrl.toString()
        return this
    }
}
