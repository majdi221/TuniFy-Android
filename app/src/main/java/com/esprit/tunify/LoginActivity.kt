package com.esprit.tunify

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.esprit.tunify.network.ApiService
import com.esprit.tunify.network.UserService
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    object Constants {
        const val SHARED_PREF_SESSION = "SESSION"
    }
    private var signUpText: TextView? = null
    private var username: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var buttonLogin: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signUpText = findViewById(R.id.signUpText)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin!!.setOnClickListener {
            login()
        }

        signUpText!!.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun login() {
        ApiService.userService.login(
            UserService.LoginBody(
                username!!.text.toString(),
                password!!.text.toString()
            )
        ).enqueue(
            object : Callback<UserService.UserResponse> {
                override fun onResponse(
                    call: Call<UserService.UserResponse>,
                    response: Response<UserService.UserResponse>
                ) {
                    if (response.code() == 200) {
                        val sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
                        val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
                        val json = Gson().toJson(response.body()!!.user)
                        sharedPreferencesEditor.putString("USER_DATA", json)
                        sharedPreferencesEditor.apply()
                        Log.d("test", "Usr=: "+sharedPreferences.getString("USER_DATA",json))
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Wrong credentials", Toast.LENGTH_SHORT).show()
                        Log.d("HTTP ERROR", "status code is " + response.code())
                    }
                }

                override fun onFailure(
                    call: Call<UserService.UserResponse>,
                    t: Throwable
                ) {
                    Log.d("FAIL", "fail")
                }
            }
        )
    }

}