package com.vulnbank.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    // M1 - Credenciais hardcoded no código-fonte
    private val ADMIN_USER = "admin"
    private val ADMIN_PASS = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin   = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val user = etUsername.text.toString()
            val pass = etPassword.text.toString()

            // M10 - Usando MD5 quebrado para comparar senha
            if (user == ADMIN_USER && md5(pass) == md5(ADMIN_PASS)) {

                // M9 - Salvando dados sensíveis em texto claro no SharedPreferences
                val prefs = getSharedPreferences("vulnbank_prefs", MODE_PRIVATE)
                prefs.edit()
                    .putString("auth_token", "TOKEN_SECRETO_12345")
                    .putString("username", user)
                    .putString("password", pass)
                    .apply()

                // M6 - Logando credenciais em texto claro
                Log.d("VulnBank", "Login OK - usuário: $user senha: $pass")

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                // M6 - Logando tentativa de login com dados do usuário
                Log.d("VulnBank", "Login FALHOU - tentativa: $user / $pass")
            }
        }
    }

    // M10 - MD5 é criptograficamente quebrado desde 2004
    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return md.digest(input.toByteArray())
            .joinToString("") { "%02x".format(it) }
    }
}
