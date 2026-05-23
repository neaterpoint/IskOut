package com.project.iskout.login

import com.project.iskout.core.SessionManager

class LoginPresenter(private var view: LoginContract.View?, private var model: LoginModel?) : LoginContract.Presenter {
    override fun onLoginClicked(username: String?, password: String?) {
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            view?.showError("Missing Fields.")
            return
        }

        // 1. Attempt login
        val user = model?.login(username, password)

        if (user != null) {
            // 2. Fetch profiles based on the role
            val studentProfile = if (user.role == "student") model?.getStudentProfile(user.user_id) else null
            val merchantProfile = if (user.role == "merchant") model?.getMerchantProfile(user.user_id) else null

            // 3. Save to SessionManager BEFORE navigating
            SessionManager.loginUser(user, studentProfile, merchantProfile)

            // 4. Navigate
            view?.navigateToLanding()
        } else {
            view?.showError("Invalid Credentials.")
        }
    }
    override fun onRegisterClicked() {
        view?.navigateToRegister()
    }

    override fun onDestroy() {
        view = null
    }

}