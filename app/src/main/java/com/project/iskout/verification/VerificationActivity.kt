package com.project.iskout.verification

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.project.iskout.R

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
        tvStudentSubtitle.setTextColor(Color.parseColor("#94A3B8"))

        // Change Student Icon inner tint to white, and its background square to solid blue
        ivStudentIcon.imageTintList = ColorStateList.valueOf(Color.WHITE)
        ivStudentIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#358DEF"))

        // 3. Set Merchant UI to 'Unselected' state
        tvMerchantTitle.setTextColor(Color.parseColor("#0F172A"))
        tvMerchantSubtitle.setTextColor(Color.parseColor("#64748B"))

        // Change Merchant Icon inner tint to blue, and clear background tint to show default light square
        ivMerchantIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#3B82F6"))
        ivMerchantIcon.backgroundTintList = null
    }

    override fun setMerchantSelectedUI() {
        // 1. Swap Card Backgrounds
        cardMerchant.setBackgroundResource(R.drawable.bg_card_selected)
        cardStudent.setBackgroundResource(R.drawable.bg_card_unselected)

        // 2. Set Merchant UI to 'Selected' state
        tvMerchantTitle.setTextColor(Color.WHITE)
        tvMerchantSubtitle.setTextColor(Color.parseColor("#94A3B8"))

        // Change Merchant Icon inner tint to white, and its background square to solid blue
        ivMerchantIcon.imageTintList = ColorStateList.valueOf(Color.WHITE)
        ivMerchantIcon.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#358DEF"))

        // 3. Set Student UI to 'Unselected' state
        tvStudentTitle.setTextColor(Color.parseColor("#0F172A"))
        tvStudentSubtitle.setTextColor(Color.parseColor("#64748B"))

        // Change Student Icon inner tint to blue, and clear background tint to show default light square
        ivStudentIcon.imageTintList = ColorStateList.valueOf(Color.parseColor("#3B82F6"))
        ivStudentIcon.backgroundTintList = null
    }

    override fun changeUploadLabelText(text: String) {
        tvUploadLabel.text = text
    }

    override fun openFilePicker() {
        Toast.makeText(this, "Opening Android File Picker...", Toast.LENGTH_SHORT).show()

        // TODO: Launch actual Intent(Intent.ACTION_GET_CONTENT) here.
        // For testing the flow right now, let's fake a successful file pick:
        //presenter.onFileSelected("my_document.pdf")
    }

    override fun setSubmitButtonEnabled(isEnabled: Boolean) {
        btnSubmit.isEnabled = isEnabled
        if (isEnabled) {
            btnSubmit.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#3B82F6"))
            btnSubmit.setTextColor(Color.WHITE)
        } else {
            btnSubmit.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#E2E8F0"))
            btnSubmit.setTextColor(Color.parseColor("#94A3B8"))
        }
    }

    override fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            btnSubmit.text = "Uploading..."
            btnSubmit.isEnabled = false
        } else {
            btnSubmit.text = "Submit for review"
            btnSubmit.isEnabled = true
        }
    }

    override fun showError(message: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_error)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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
        Toast.makeText(this, "Success! Moving to Step 3.", Toast.LENGTH_SHORT).show()
        // Intent to next activity
    }

    override fun navigateBack() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}