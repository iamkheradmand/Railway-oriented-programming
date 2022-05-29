package com.test.rop.ui.login

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.rop.databinding.ActivityLoginBinding

import com.test.rop.data.model.ROPResult
import com.test.rop.data.model.onFailure
import com.test.rop.data.model.onSuccess
import com.test.rop.data.model.then

/**
 * Created by Amir Mohammad Kheradmand on 5/29/2022.
 */

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            validation()
        }
    }

    private fun validation(){
        val id = binding.id!!.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        notEmpty(id, "Enter your id")
            .then { notEmpty(email, "Enter your Email") }
            .then { isValidEmail(email, "Email is not valid") }
            .then { notEmpty(password, "Enter your password") }
            .then { checkPassLength(password, "Invalid password") }
            .onFailure {
                //show error message
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }.onSuccess {
                Toast.makeText(this, "Validation was Successful", Toast.LENGTH_SHORT).show()
            }
    }

    private fun notEmpty(text: String, errMessage: String) =
        if (text.isNotEmpty())
            ROPResult.Success(text)
        else
            ROPResult.Failure(errMessage)

    private fun isValidEmail(text: String, errMessage: String) =
        if (Patterns.EMAIL_ADDRESS.matcher(text).matches())
            ROPResult.Success(text)
        else
            ROPResult.Failure(errMessage)

    private fun checkPassLength(newPass: String, errMessage: String) =
        if (newPass.length >= 8)
            ROPResult.Success(newPass)
        else
            ROPResult.Failure(errMessage)
}