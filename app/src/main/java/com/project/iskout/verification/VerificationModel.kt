package com.project.iskout.verification

class VerificationModel {

    // Store the raw state here, away from the UI
    var selectedRole: String = "Student" // Default based on your XML
    var attachedFileName: String? = null

    // Simulating a network request to upload the document
    fun submitDocument(role: String, fileName: String): Boolean {
        // TODO: In the future, this is where you call your API / Retrofit
        // Example: api.uploadVerification(role, file)

        // For now, if a file exists, we consider it a success
        return fileName.isNotEmpty()
    }
}