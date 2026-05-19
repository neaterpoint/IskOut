package com.project.iskout.core

data class User(
    val username: String,
    val firstName: String,
    val lastName: String
)

// The 'object' keyword makes this a Singleton.
// You can access SessionManager from ANY Activity or Presenter!
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