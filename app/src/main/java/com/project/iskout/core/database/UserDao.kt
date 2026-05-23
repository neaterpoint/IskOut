package com.project.iskout.database

import androidx.room.Dao
import androidx.room.Query
import com.project.iskout.core.database.entities.users.MerchantProfile
import com.project.iskout.core.database.entities.users.StudentProfile
import com.project.iskout.core.database.entities.users.User

@Dao
interface UserDao {

    // This is your RetrieveData function tied to login!
    // It looks for a user with the exact username and password.
    // If it finds one, it returns the User object. If not, it returns null.
    @Query("SELECT * FROM users WHERE (email = :usernameInput) AND password_hash = :passwordInput LIMIT 1")
    fun login(usernameInput: String, passwordInput: String): User?

    @Query("SELECT * FROM student_profiles WHERE user_id = :userId LIMIT 1")
    fun getStudentProfile(userId: String): StudentProfile?

    @Query("SELECT * FROM merchant_profiles WHERE user_id = :userId LIMIT 1")
    fun getMerchantProfile(userId: String): MerchantProfile?

}