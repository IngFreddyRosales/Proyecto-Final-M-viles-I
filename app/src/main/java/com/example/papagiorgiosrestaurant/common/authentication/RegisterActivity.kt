package com.example.papagiorgiosrestaurant.common.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.common.authentication.viewModels.RegisterViewModel
import com.example.papagiorgiosrestaurant.common.models.Profile
import com.example.papagiorgiosrestaurant.common.models.User
import com.example.papagiorgiosrestaurant.databinding.ActivityMainBinding
import com.example.papagiorgiosrestaurant.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var bindingRegisterBinding: ActivityRegisterBinding
    private val registerViewMode: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bindingRegisterBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindingRegisterBinding.btnRegisters.setOnClickListener {
            val name = bindingRegisterBinding.txtName.text.toString()
            val email = bindingRegisterBinding.txtEmail.text.toString()
            val password = bindingRegisterBinding.txtPassword.text.toString()
            val selectedRole = when (bindingRegisterBinding.spinnerRolUser.selectedItem.toString()) {
                "Cliente" -> 1
                "Repartidor" -> 2
                else -> 1
            }

            val profile = Profile(id = 0, role = selectedRole)
            val user = User(
                id = 0,
                name = name,
                email = email,
                password = password,
                profile = profile
            )

            println("User activity: $user")
            registerViewMode.registerUser(user)
        }

//        var selectedRole = 1
//
//        bindingRegisterBinding.spinnerRolUser.onItemSelectedListener =
//            object : AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    id: Long
//                ) {
//                    selectedRole = when (roles[position]) {
//                        "Cliente" -> 1
//                        "Repartidor" -> 2
//                        else -> 1
//                    }
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>?) {
//                    selectedRole = 1
//                }
//            }
//
//        // observar los resultados del viewModel
//        setupObservers()
//
//        bindingRegisterBinding.btnRegisters.setOnClickListener {
//
//            val name = bindingRegisterBinding.txtName.text.toString()
//            val email = bindingRegisterBinding.txtEmail.text.toString()
//            val password = bindingRegisterBinding.txtPassword.text.toString()
//
//            val user = User(
//                name = name,
//                email = email,
//                password = password,
//                role = selectedRole
//            )
//
//            println("User activity: $user")
//            registerViewMode.registerUser(user)
//        }


    }

    private fun setupObservers() {
        registerViewMode.success.observe(this) { isSuccess ->
            if (isSuccess) {
                "Usuario registrado exitosamente"
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        registerViewMode.error.observe(this) { errorMessage ->
            if (errorMessage != "409") {
                Toast.makeText(this, "El usuario ya esta registrado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
            }
        }

    }




}


