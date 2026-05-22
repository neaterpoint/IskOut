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
import android.widget.Toast
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

        etEmail = findViewById<EditText>(R.id.etEmail)
        etPassword = findViewById<EditText>(R.id.etPassword)
        btnLogin = findViewById<Button>(R.id.btnLogin)
        tvRegister = findViewById<TextView>(R.id.tvRegister)
        //Database integration
        val db = DatabaseConnection.getDatabase(this)
        // 2. Pass the database into the model
        presenter = LoginPresenter(this, LoginModel(db))

        btnLogin.setOnClickListener {
            val username:String = etEmail.text.toString()
            val password:String = etPassword.text.toString()
            presenter.onLoginClicked(username,password)
        }

        tvRegister.setOnClickListener {
            presenter.onRegisterClicked()
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

    override fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun navigateToLanding() {
//        val intent = Intent(this, MapPageActivity::class.java)
//        startActivity(intent)
        Toast.makeText(this, "Going to Homepage ;)", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}