package com.project.iskout.login;

public class LoginModel {
    // These properties are fine to keep if you plan to use them to hold the logged-in user's state later
    var username: String? = null
    var first_name: String? = null
    var last_name: String? = null

    fun login(usernameInput: String?, passwordInput: String?): Boolean {
        // TODO: return com.practice.mvp_vs_practice2.data.UserRepository.login(username, password)
        // Fetches database dbUsers to check user existence in the future.

        // For the sake of making the code compile and run for your exam today:
        return usernameInput == "admin" && passwordInput == "123"
    }
}
