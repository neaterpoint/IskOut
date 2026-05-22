package com.project.iskout.core

import com.project.iskout.core.database.entities.users.User

object SessionManager {
    var currentUser: User? = null

    fun loginUser(user: User) {
        currentUser = user
    }

    fun logout() {
        currentUser = null
    }

    fun isLoggedIn(): Boolean {
        return currentUser != null
    }
}