package com.project.iskout.register

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.project.iskout.R
import com.project.iskout.verification.VerificationActivity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.Window

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var etFullName: TextInputEditText
    private lateinit var etRegisterEmail: TextInputEditText
    private lateinit var etRegisterPassword: TextInputEditText
    private lateinit var etConfirmPassword: TextInputEditText
    private lateinit var btnRegisterSubmit: MaterialButton
    private lateinit var tvLoginRedirect: TextView

    private lateinit var presenter: RegisterContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etFullName = findViewById(R.id.etFullName)
        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterPassword = findViewById(R.id.etRegisterPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit)
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect)

        presenter = RegisterPresenter(this, RegisterModel())

        btnRegisterSubmit.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etRegisterEmail.text.toString().trim()
            val password = etRegisterPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            presenter.onRegisterClicked(fullName, email, password, confirmPassword)
        }

        tvLoginRedirect.setOnClickListener {
            presenter.onLoginRedirectClicked()
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


    override fun navigateToLogin() {
        // Since they likely came from LoginActivity, simply finishing this activity
        // will pop them back to the Login screen perfectly.
        finish()
    }

    override fun navigateToVerification() {
        var intent = Intent(this, VerificationActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}