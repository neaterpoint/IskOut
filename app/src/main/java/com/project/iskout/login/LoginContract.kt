package com.project.iskout.login

interface LoginContract {

    interface View{
        fun showError(message: String)
        fun navigateToRegister()
        fun navigateToLanding()
    }

    interface Presenter{
        fun onLoginClicked(username:String?, password:String?)
        fun onRegisterClicked()
        fun onDestroy()

    }

}