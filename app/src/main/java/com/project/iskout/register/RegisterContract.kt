package com.project.iskout.register

interface RegisterContract {

    interface View {
        fun showError(message: String)
        fun navigateToLogin()
        fun navigateToVerification()
    }

    interface Presenter {
        fun onRegisterClicked(fullName: String?, email: String?, password: String?, confirmPassword: String?)
        fun onLoginRedirectClicked()
        fun onDestroy()
    }
}