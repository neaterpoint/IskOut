package com.project.iskout.verification

interface VerificationContract {

    interface View {
        // UI Updates for Role Selection
        fun setStudentSelectedUI()
        fun setMerchantSelectedUI()
        fun changeUploadLabelText(text: String)

        // Upload and Submit actions
        fun openFilePicker()
        fun setSubmitButtonEnabled(isEnabled: Boolean)

        // Navigation and Feedback
        fun showLoading(isLoading: Boolean)
        fun showError(message: String)
        fun navigateToNextStep()
        fun navigateBack()
    }

    interface Presenter {
        fun onBackClicked()
        fun onStudentRoleSelected()
        fun onMerchantRoleSelected()
        fun onUploadAreaClicked()
        fun onFileSelected(fileName: String) // Called when user picks a file
        fun onSubmitClicked()
        fun onDestroy()
    }
}