package com.vulnbank.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.net.HttpURLConnection
import java.net.URL

class TransferActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)

        val etAmount      = findViewById<EditText>(R.id.etAmount)
        val etDestination = findViewById<EditText>(R.id.etDestination)
        val btnTransfer   = findViewById<Button>(R.id.btnTransfer)
        val tvResult      = findViewById<TextView>(R.id.tvResult)

        btnTransfer.setOnClickListener {

            // M4 - Sanitização e validação de input
            val destination = etDestination.text.toString()
                .replace(Regex("[^0-9-]"), "")
            val amountText  = etAmount.text.toString()
            val amount      = amountText.toDoubleOrNull()

            if (destination.isBlank() || destination.length < 5) {
                tvResult.text = "Conta destino inválida"
                return@setOnClickListener
            }

            if (amount == null || amount <= 0 || amount > 10000) {
                tvResult.text = "Valor inválido. Insira um valor entre R\$0,01 e R\$10.000,00"
                return@setOnClickListener
            }

            // M3 - Verificar sessão antes de prosseguir
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

            val token = prefs.getString("auth_token", "")

            // M3 - Token ausente redireciona para login
            if (token.isNullOrBlank()) {
                tvResult.text = "Sessão expirada. Faça login novamente"
                startActivity(Intent(this, LoginActivity::class.java))
                return@setOnClickListener
            }

            // M5 - HTTPS obrigatório
            Thread {
                try {
                    val url = URL("https://10.0.2.2:8443/transfer")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.doOutput = true
                    connection.setRequestProperty("Authorization", "Bearer $token")
                    connection.setRequestProperty("Content-Type", "application/json")

                    val body = """
                        {
                            "destination": "$destination",
                            "amount": $amount
                        }
                    """.trimIndent()

                    // M6 - Log sem dados sensíveis
                    Log.d("VulnBank", "Transferência iniciada")

                    connection.outputStream.write(body.toByteArray())
                    val responseCode = connection.responseCode

                    runOnUiThread {
                        tvResult.text = "Transferência enviada! Código: $responseCode"
                    }

                } catch (e: Exception) {
                    runOnUiThread {
                        // M6 - Erro genérico sem detalhes internos
                        tvResult.text = "Erro ao processar transferência"
                    }
                }
            }.start()
        }
    }
}
