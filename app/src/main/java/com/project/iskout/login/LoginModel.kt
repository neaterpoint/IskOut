package com.project.iskout.login

import com.project.iskout.database.DatabaseConnection
import com.project.iskout.core.database.entities.users.User

// Pass the database into the model so it can retrieve data
class LoginModel(private val database: DatabaseConnection) {

    var loggedInUser: User? = null

    fun login(usernameInput: String, passwordInput: String): User? {
        return database.userDao().login(usernameInput, passwordInput)
    }

    fun getStudentProfile(userId: String) = database.userDao().getStudentProfile(userId)
    fun getMerchantProfile(userId: String) = database.userDao().getMerchantProfile(userId)

}