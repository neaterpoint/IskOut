package com.project.iskout.login

class LoginPresenter(private var view: LoginContract.View?, private var model: LoginModel?) : LoginContract.Presenter {
    override fun onLoginClicked(username:String?, password:String?) {
        if(username.isNullOrEmpty() || password.isNullOrEmpty()){
            view?.showError("Missing Fields.");
            return
        }

        val isSuccess = model?.login(username,password)

        if(isSuccess == true){
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