package com.project.iskout.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.project.iskout.R
import com.project.iskout.database.DatabaseConnection
import com.project.iskout.homepage.map.MapPageActivity
import com.project.iskout.register.RegisterActivity

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView

    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        // Database integration
        val db = DatabaseConnection.getDatabase(this)
        // Pass the database into the model
        presenter = LoginPresenter(this, LoginModel(db))

        btnLogin.setOnClickListener {
            val username = etEmail.text.toString()
            val password = etPassword.text.toString()

            // 1. Instant UI Feedback (Prevents the rigid freeze)
            btnLogin.text = "Loading..."
            btnLogin.isEnabled = false

            // 2. Delay the heavy MVP check and Activity transition slightly
            // This gives the Android UI thread just enough time to visually show the button changing
            Handler(Looper.getMainLooper()).postDelayed({
                presenter.onLoginClicked(username, password)
            }, 150)
        }

        tvRegister.setOnClickListener {
            presenter.onRegisterClicked()
        }
    }

    override fun showError(message: String) {
        // Reset the button state in case the login failed
        btnLogin.text = "Login"
        btnLogin.isEnabled = true

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

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToLanding() {
        val intent = Intent(this, MapPageActivity::class.java)
        startActivity(intent)
        // Finish this activity so the user can't press 'Back' into the login screen
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}