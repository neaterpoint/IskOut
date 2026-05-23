package com.project.iskout.core

import com.project.iskout.core.database.entities.users.MerchantProfile
import com.project.iskout.core.database.entities.users.StudentProfile
import com.project.iskout.core.database.entities.users.User

object SessionManager {
    var currentUser: User? = null
    var studentProfile: StudentProfile? = null
    var merchantProfile: MerchantProfile? = null

    // Call this upon successful login to cache all user data
    fun loginUser(user: User, student: StudentProfile? = null, merchant: MerchantProfile? = null) {
        currentUser = user
        studentProfile = student
        merchantProfile = merchant
    }

    fun logout() {
        currentUser = null
        studentProfile = null
        merchantProfile = null
    }

    fun isLoggedIn(): Boolean = currentUser != null

    // Helpers to easily get display info
    fun getDisplayName(): String = currentUser?.full_name ?: "Guest"

    fun getAcademicInfo(): String {
        return if (studentProfile != null) {
            "🎓 ${studentProfile?.school_name} · BS CS"
        } else if (merchantProfile != null) {
            "💼 ${merchantProfile?.business_name}"
        } else {
            "User"
        }
    }
}