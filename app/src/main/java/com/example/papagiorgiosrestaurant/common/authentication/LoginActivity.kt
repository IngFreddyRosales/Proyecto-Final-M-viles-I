package com.example.papagiorgiosrestaurant.common.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.papagiorgiosrestaurant.MainActivity.MainActivity
import com.example.papagiorgiosrestaurant.R
import com.example.papagiorgiosrestaurant.client.ui.RestaurantListActivity
import com.example.papagiorgiosrestaurant.common.api.repository.LoginRepository
import com.example.papagiorgiosrestaurant.common.authentication.viewModels.LoginViewModel
import com.example.papagiorgiosrestaurant.common.models.LoginRequest
import com.example.papagiorgiosrestaurant.databinding.ActivityLoginBinding
import com.example.papagiorgiosrestaurant.driver.ui.OrderListActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding
    private val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingLogin = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingLogin.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindingLogin.btnLogin.setOnClickListener {
            val email = bindingLogin.txtEmail.text.toString()
            val password = bindingLogin.txtPassword.text.toString()
            val loginRequest = LoginRequest(email, password)

            if(validateInput(email, password)){
                Toast.makeText(this, "Iniciando sesión", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }

            LoginRepository.loginUser(
                loginRequest,
                onSuccess = {
                    if(MainActivity.access_token.isNotEmpty()){
                        LoginRepository.getInfoUser(
                            onSuccess = { role ->
                                println("Rolesss: $role")
                                if (role == 1) {
                                    val intent = Intent(this, RestaurantListActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this, OrderListActivity::class.java)
                                    startActivity(intent)
                                }
                            },
                            onError = {
                                println("Error al obtener la información del usuario")
                            }
                        )
                    } else {
                        println("No se pudo obtener el token")
                    }
                },
                onError = {
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            )

        }



        bindingLogin.btnToResgister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }




    private fun validateInput(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }
}