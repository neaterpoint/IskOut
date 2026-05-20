package com.project.iskout.verification

import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.OpenableColumns
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.project.iskout.R
import com.project.iskout.confirmation.ConfirmationActivity
import androidx.core.graphics.toColorInt
import androidx.core.graphics.drawable.toDrawable

class VerificationActivity : AppCompatActivity(), VerificationContract.View {
    private lateinit var ivStudentIcon: ImageView
    private lateinit var tvStudentTitle: TextView
    private lateinit var tvStudentSubtitle: TextView
    private lateinit var ivMerchantIcon: ImageView
    private lateinit var tvMerchantTitle: TextView
    private lateinit var tvMerchantSubtitle: TextView

    private lateinit var presenter: VerificationContract.Presenter

    private lateinit var btnBack: ImageView
    private lateinit var cardStudent: LinearLayout
    private lateinit var cardMerchant: LinearLayout
    private lateinit var tvUploadLabel: TextView
    private lateinit var layoutUploadArea: LinearLayout
    private lateinit var btnSubmit: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        ivStudentIcon = findViewById(R.id.ivStudentIcon)
        tvStudentTitle = findViewById(R.id.tvStudentTitle)
        tvStudentSubtitle = findViewById(R.id.tvStudentSubtitle)
        ivMerchantIcon = findViewById(R.id.ivMerchantIcon)
        tvMerchantTitle = findViewById(R.id.tvMerchantTitle)
        tvMerchantSubtitle = findViewById(R.id.tvMerchantSubtitle)
        btnBack = findViewById(R.id.btnBack)
        cardStudent = findViewById(R.id.cardStudent)
        cardMerchant = findViewById(R.id.cardMerchant)
        tvUploadLabel = findViewById(R.id.tvUploadLabel)
        layoutUploadArea = findViewById(R.id.layoutUploadArea)
        btnSubmit = findViewById(R.id.btnSubmit)

        presenter = VerificationPresenter(this, VerificationModel())

        btnBack.setOnClickListener { presenter.onBackClicked() }
        cardStudent.setOnClickListener { presenter.onStudentRoleSelected() }
        cardMerchant.setOnClickListener { presenter.onMerchantRoleSelected() }
        layoutUploadArea.setOnClickListener { presenter.onUploadAreaClicked() }
        btnSubmit.setOnClickListener { presenter.onSubmitClicked() }
    }


    override fun setStudentSelectedUI() {
        // 1. Swap Card Backgrounds
        cardStudent.setBackgroundResource(R.drawable.bg_card_selected)
        cardMerchant.setBackgroundResource(R.drawable.bg_card_unselected)

        // 2. Set Student UI to 'Selected' state
        tvStudentTitle.setTextColor(Color.WHITE)
        tvStudentSubtitle.setTextColor("#94A3B8".toColorInt())

        // Change Student Icon inner tint to white, and its background square to solid blue
        ivStudentIcon.imageTintList = ColorStateList.valueOf(Color.WHITE)
        ivStudentIcon.backgroundTintList = ColorStateList.valueOf("#358DEF".toColorInt())

        // 3. Set Merchant UI to 'Unselected' state
        tvMerchantTitle.setTextColor("#0F172A".toColorInt())
        tvMerchantSubtitle.setTextColor("#64748B".toColorInt())

        // Change Merchant Icon inner tint to blue, and clear background tint to show default light square
        ivMerchantIcon.imageTintList = ColorStateList.valueOf("#3B82F6".toColorInt())
        ivMerchantIcon.backgroundTintList = null
    }

    override fun setMerchantSelectedUI() {
        // 1. Swap Card Backgrounds
        cardMerchant.setBackgroundResource(R.drawable.bg_card_selected)
        cardStudent.setBackgroundResource(R.drawable.bg_card_unselected)

        // 2. Set Merchant UI to 'Selected' state
        tvMerchantTitle.setTextColor(Color.WHITE)
        tvMerchantSubtitle.setTextColor("#94A3B8".toColorInt())

        // Change Merchant Icon inner tint to white, and its background square to solid blue
        ivMerchantIcon.imageTintList = ColorStateList.valueOf(Color.WHITE)
        ivMerchantIcon.backgroundTintList = ColorStateList.valueOf("#358DEF".toColorInt())

        // 3. Set Student UI to 'Unselected' state
        tvStudentTitle.setTextColor("#0F172A".toColorInt())
        tvStudentSubtitle.setTextColor("#64748B".toColorInt())

        // Change Student Icon inner tint to blue, and clear background tint to show default light square
        ivStudentIcon.imageTintList = ColorStateList.valueOf("#3B82F6".toColorInt())
        ivStudentIcon.backgroundTintList = null
    }

    override fun changeUploadLabelText(text: String) {
        tvUploadLabel.text = text
    }

    override fun openFilePicker() {
        // Define the exact file types you want to allow
        val mimeTypes = arrayOf(
            "image/jpeg",      // Allows .jpg and .jpeg
            "image/png",       // Allows .png
            "application/pdf"  // Allows .pdf
        )

        // Launch the picker with our specific rules
        filePickerLauncher.launch(mimeTypes)
    }
    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        if (uri != null) {
            val fileName = getFileNameFromUri(uri)
            presenter.onFileSelected(fileName)
            Toast.makeText(this, "Selected: $fileName", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }
    // Helper function to extract the real file name from an Android URI
    private fun getFileNameFromUri(uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    // Get the name column index
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (nameIndex >= 0) {
                        result = cursor.getString(nameIndex)
                    }
                }
            } finally {
                cursor?.close()
            }
        }
        // Fallback if the above fails
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/') ?: -1
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result ?: "Unknown_File"
    }

    override fun setSubmitButtonEnabled(isEnabled: Boolean) {
        btnSubmit.isEnabled = isEnabled
        if (isEnabled) {
            btnSubmit.backgroundTintList = ColorStateList.valueOf("#3B82F6".toColorInt())
            btnSubmit.setTextColor(Color.WHITE)
        } else {
            btnSubmit.backgroundTintList = ColorStateList.valueOf("#E2E8F0".toColorInt())
            btnSubmit.setTextColor("#94A3B8".toColorInt())
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            btnSubmit.text = getString(R.string.uploading_text)
            btnSubmit.isEnabled = false
        } else {
            btnSubmit.text = getString(R.string.submit_for_review)
            btnSubmit.isEnabled = true
        }
    }

    override fun showError(message: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_error)
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        // Allows the user to dismiss it early by tapping outside the box
        dialog.setCancelable(true)

        val tvMessage = dialog.findViewById<TextView>(R.id.tvErrorMessage)
        tvMessage.text = message

        // Show the popup
        dialog.show()

        // 2-Second Auto-Dismiss Timer
        Handler(Looper.getMainLooper()).postDelayed({
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }, 2000)
    }

    override fun navigateToNextStep() {
        val intent = Intent(this,ConfirmationActivity::class.java)
        startActivity(intent)
    }

    override fun navigateBack() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}