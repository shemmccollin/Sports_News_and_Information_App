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
import com.example.sportsnewsandinformationapp.databinding.ActivityRegisterBinding
import com.example.sportsnewsandinformationapp.modals.DBHelper

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.register) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onRegister(view: View) {
        if(binding.username.text.toString().isBlank())
        {
            Toast.makeText(this, "Username can not be empty", Toast.LENGTH_LONG).show()
        }
        else if(binding.password.text.toString().isBlank())
        {
            Toast.makeText(this, "Password can not be empty", Toast.LENGTH_LONG).show()
        }
        else if(binding.confirmPassword.text.toString().isBlank())
        {
            Toast.makeText(this, "Confirm Password can not be empty", Toast.LENGTH_LONG).show()
        }
        else if(binding.confirmPassword.text.toString() != binding.password.text.toString())
        {
            Toast.makeText(this, "Password and Confirm Password must match", Toast.LENGTH_LONG).show()
        }
        else
        {
            val db = DBHelper(this, null)
            db.addUser(binding.username.text.toString(), binding.password.text.toString())
            Toast.makeText(this, "User added.", Toast.LENGTH_LONG).show()
            onCancel(view)
        }
    }

    fun onCancel(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}