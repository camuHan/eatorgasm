package com.cason.eatorgasm.define

object EatDefine {

    object Request {
        const val REQUEST_KEY = "requestKey"
    }

    object Result {
        const val RESULT_UPDATE_PROFILE = "result_update_profile"
        const val RESULT_EDIT_PROFILE = "result_edit_profile"
    }

    object BundleKey {
        const val BOARD_ID = "board_id"
        const val BOARD_INFO_MODEL = "board_info_model"
    }

    object TransitionName {
        const val IMAGE_TRANSITION = "image_transition"
        const val TITLE_TRANSITION = "title_transition"
        const val PROFILE_TRANSITION = "profile_transition"
    }

    object FireStoreCollection {
        const val COLLECTION_NAME_USERS = "users"
        const val COLLECTION_NAME_BOARDS = "boards"
        /* not used */
        const val COLLECTION_NAME_PROFILE_IMAGES = "profileImages"
    }

    object FireBaseStorage {
        const val FIREBASE_STORAGE_PROFILE_IMAGES = "userProfileImages"
        const val FIREBASE_STORAGE_BOARD_IMAGES = "boardImages"
    }

    /*  not used  */

    const val RESULT_PATH = "result_path"
    const val RESULT_OPEN = "result_open"

    const val RESULT_NEW_FOLDER = "result_new_folder"

    const val BUNDLE_CURRENT_PATH   = "current_path"
    const val BUNDLE_PATH_SELECT_TYPE = "path_select_type"
    const val BUNDLE_PATH_SELECT_TYPE_MOVE= "path_select_type_move"
    const val BUNDLE_PATH_SELECT_TYPE_COPY= "path_select_type_copy"
    const val BUNDLE_PATH_SELECT_TYPE_START= "path_select_type_start"
    const val BUNDLE_CURRENT_STATUS = "current_status"

    const val COMMON_DIALOG_TAG = "dialog"
}