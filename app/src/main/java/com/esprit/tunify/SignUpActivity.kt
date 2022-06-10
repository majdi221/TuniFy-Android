package com.esprit.tunify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.esprit.tunify.network.ApiService
import com.esprit.tunify.network.UserService
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private var firstname: TextInputEditText? = null
    private var lastname: TextInputEditText? = null
    private var email: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var adress: TextInputEditText? = null
    private var phone: TextInputEditText? = null
    private var buttonSignUp: Button? = null
    private var loginButton: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        adress = findViewById(R.id.adress)
        phone = findViewById(R.id.phone)
        buttonSignUp = findViewById(R.id.buttonSignUp)
        loginButton = findViewById(R.id.loginButton)

        buttonSignUp!!.setOnClickListener {
            ApiService.userService.register(
                UserService.UserBody(
                    firstname!!.text.toString(),
                    lastname!!.text.toString(),
                    email!!.text.toString(),
                    password!!.text.toString(),
                    adress!!.text.toString(),
                    phone!!.text.toString()
                )
            ).enqueue(
                object : Callback<UserService.UserResponse> {
                    override fun onResponse(
                        call: Call<UserService.UserResponse>,
                        response: Response<UserService.UserResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
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

        loginButton!!.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}