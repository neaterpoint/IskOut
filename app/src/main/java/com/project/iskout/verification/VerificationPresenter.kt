package com.project.iskout.verification

class VerificationPresenter(
    private var view: VerificationContract.View?,
    private var model: VerificationModel
) : VerificationContract.Presenter {

    // When the screen first loads, ensure the UI matches our Model's default state
    init {
        view?.setStudentSelectedUI()
        view?.changeUploadLabelText("Upload your School ID")
    }

    override fun onBackClicked() {
        view?.navigateBack()
    }

    override fun onStudentRoleSelected() {
        model.selectedRole = "Student"
        view?.setStudentSelectedUI()
        view?.changeUploadLabelText("Upload your School ID")
    }

    override fun onMerchantRoleSelected() {
        model.selectedRole = "Merchant"
        view?.setMerchantSelectedUI()
        view?.changeUploadLabelText("Upload your business permit")
    }

    override fun onUploadAreaClicked() {
        view?.openFilePicker()
    }

    override fun onFileSelected(fileName: String) {
        model.attachedFileName = fileName
        // Once a file is selected, tell the view to turn the button blue and enable it
        view?.setSubmitButtonEnabled(true)
    }

    override fun onSubmitClicked() {
        val currentRole = model.selectedRole
        val file = model.attachedFileName

        if (file == null) {
            view?.showError("Please upload a document first.")
            return
        }

        view?.showLoading(true)

        // Ask Model to handle the business logic
        val isSuccess = model.submitDocument(currentRole, file)

        view?.showLoading(false)

        if (isSuccess) {
            view?.navigateToNextStep()
        } else {
            view?.showError("Upload failed. Please try again.")
        }
    }

    override fun onDestroy() {
        view = null
    }
}