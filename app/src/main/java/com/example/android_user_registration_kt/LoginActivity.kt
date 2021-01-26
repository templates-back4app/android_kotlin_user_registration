package com.example.android_user_registration_kt

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {

    private var username: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var login: Button? = null
    private var navigatesignup: Button? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        progressDialog = ProgressDialog(this@LoginActivity)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        login = findViewById(R.id.login)
        navigatesignup = findViewById(R.id.navigatesignup)

        login?.setOnClickListener(View.OnClickListener {
            login(
                username?.text.toString(),
                password?.text.toString()
            )
        })

        navigatesignup?.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    SignUpActivity::class.java
                )
            )
        })

    }

    fun login(username: String, password: String) {
        progressDialog?.show();
        ParseUser.logInInBackground(
            username,
            password,
            LogInCallback() { parseUser: ParseUser?, parseException: ParseException? ->
                progressDialog?.dismiss()
                if (parseUser != null) {
                    showAlert("Sucessful Login", "Welcome back " + username + " !");
                } else {
                    ParseUser.logOut();
                    if (parseException != null) {
                        Toast.makeText(this, parseException.message, Toast.LENGTH_LONG).show()
                    };
                }

            });
    }


    private fun showAlert(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                // don't forget to change the line below with the names of your Activities
                val intent = Intent(this, LogoutActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        val ok = builder.create()
        ok.show()
    }
}