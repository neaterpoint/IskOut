package com.project.iskout.login

import com.project.iskout.database.DatabaseConnection
import com.project.iskout.core.database.entities.users.User

// Pass the database into the model so it can retrieve data
class LoginModel(private val database: DatabaseConnection) {

    var loggedInUser: User? = null

    fun login(usernameInput: String, passwordInput: String): Boolean {
        // 1. Ask the DAO (RetrieveData) to find the user
        val user = database.userDao().login(usernameInput, passwordInput)

        // 2. If user is NOT null, the login was successful!
        if (user != null) {
            loggedInUser = user // Save the state of the logged-in user
            return true
        }

        // 3. If user is null, wrong credentials
        return false
    }
}