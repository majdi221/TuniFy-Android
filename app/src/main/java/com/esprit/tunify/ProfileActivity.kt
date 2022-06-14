package com.esprit.tunify

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.esprit.tunify.model.User
import com.google.gson.Gson

class ProfileActivity : AppCompatActivity() {

    private var firstname : TextView? = null
    private var lastname : TextView? = null
    private var email : TextView? = null
    private var phone : TextView? = null
    private var adress : TextView? = null
    private var buttonEditProfile : Button? = null
    private var buttonLogout : Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sp: SharedPreferences = getSharedPreferences(LoginActivity.Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
        val user = sp.getString("USER_DATA", null)
        val sessionUser: User? = Gson().fromJson(user, User::class.java)

        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        adress = findViewById(R.id.adress)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonEditProfile = findViewById(R.id.buttonEditProfile)


        firstname!!.text = sessionUser?.name
        lastname!!.text = sessionUser?.lastname
        email!!.text = sessionUser?.email
        phone!!.text = sessionUser?.phone
        adress!!.text = sessionUser?.adress

        buttonLogout!!.setOnClickListener {
            val sharedPreferences = getSharedPreferences(LoginActivity.Constants.SHARED_PREF_SESSION, MODE_PRIVATE)
            val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()
            sharedPreferencesEditor.clear().apply()

            val intent = Intent(this@ProfileActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        /*buttonEditProfile!!.setOnClickListener {
            //todo
        }*/
    }
}