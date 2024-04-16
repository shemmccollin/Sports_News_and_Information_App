package com.example.sportsnewsandinformationapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sportsnewsandinformationapp.databinding.ActivityMainBinding
import com.example.sportsnewsandinformationapp.modals.DBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //check SharePreferences
        val prefs = getSharedPreferences("loginContext", MODE_PRIVATE)

        val username: String? = prefs.getString("username", "")

        val password: String? = prefs.getString("password", "")


        if(!username.isNullOrBlank() && !password.isNullOrBlank())
        {
            checkUser(username, password)
        }

    }

    fun onSignIn(view: View) {
        if(binding.username.text.toString().isBlank())
        {
            Toast.makeText(this, "Username can not be empty", Toast.LENGTH_LONG).show()
        }
        else if (binding.password.text.toString().isBlank())
        {
            Toast.makeText(this, "Password can not be empty", Toast.LENGTH_LONG).show()
        }
        else
        {
            val db = DBHelper(this, null)

            val cursor = db.doesUserExist(binding.username.text.toString(), binding.password.text.toString())
            if(cursor) {
                val prefs = getSharedPreferences("loginContext", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("username", binding.username.text.toString())
                editor.putString("password", binding.password.text.toString())
                editor.apply()

                val intent = Intent(this, ContainerActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                Toast.makeText(this, "Username or Password is incorrect", Toast.LENGTH_LONG).show()
            }
        }


    }
    private fun checkUser(username: String, password: String){
        val db = DBHelper(this, null)

        val cursor = db.doesUserExist(username, password)
        if(cursor) {
            val intent = Intent(this, ContainerActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun onRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}