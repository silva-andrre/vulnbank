package com.vulnbank.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import org.mindrot.jbcrypt.BCrypt

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin   = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString()

            // M4 - Validação de input
            if (user.isBlank() || pass.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // M10 - BCrypt para verificação segura de senha
            val storedHash = BCrypt.hashpw(BuildConfig.ADMIN_PASS, BCrypt.gensalt())

            if (user == BuildConfig.ADMIN_USER && BCrypt.checkpw(pass, storedHash)) {

                // M9 - EncryptedSharedPreferences
                val masterKey = MasterKey.Builder(this)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build()

                val prefs = EncryptedSharedPreferences.create(
                    this,
                    "vulnbank_prefs_secure",
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )

                // M9 - Senha nunca armazenada
                prefs.edit()
                    .putString("auth_token", "TOKEN_SECRETO_12345")
                    .putString("username", user)
                    .apply()

                // M6 - Log sem dados sensíveis
                Log.d("VulnBank", "Login realizado com sucesso")

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show()
                Log.d("VulnBank", "Tentativa de login inválida")
            }
        }
    }
}
