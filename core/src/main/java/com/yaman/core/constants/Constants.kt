package com.yaman.core.constants

import com.yaman.core.BuildConfig

object Constants {


    const val API_URL = BuildConfig.BASE_URL

    const val HEADER_API_VERSION = "x-apiversion"
    const val HEADER_CLIENT_USER_AGENT = "x-client-useragent"
    const val HEADER_CLIENT_ID = "x-clientid"
    const val HEADER_CLIENT_PASS = "x-clientpass"
    const val CLIENT_ID = "ANDROID"
    const val CLIENT_PASS = "B27EED4A6C25BA1E95FF687476CD6"

    object ToDoDbName {
        const val DB_NAME = "todolist"
    }
}
