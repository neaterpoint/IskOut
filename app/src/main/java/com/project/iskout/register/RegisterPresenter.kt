package com.project.iskout.register

class RegisterPresenter(
    private var view: RegisterContract.View?,
    private var model: RegisterModel?
) : RegisterContract.Presenter {

    override fun onRegisterClicked(fullName: String?, email: String?, password: String?, confirmPassword: String?) {
        if (fullName.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
            view?.showError("Please fill in all fields.")
            return
        }

        if (password != confirmPassword) {
            view?.showError("Passwords do not match.")
            return
        }

        if (password.length < 6) {
            view?.showError("Password must be at least 6 characters.")
            return
        }

        val isSuccess = model?.registerUser(fullName, email, password)

        if (isSuccess == true) {
            view?.navigateToVerification()
        } else {
            view?.showError("Registration failed. Please try again.")
        }
    }

    override fun onLoginRedirectClicked() {
        view?.navigateToLogin()
    }

    override fun onDestroy() {
        view = null
    }
}