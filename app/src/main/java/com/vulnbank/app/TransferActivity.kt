package com.vulnbank.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            val amount      = etAmount.text.toString()
            val destination = etDestination.text.toString()

            // M3 - Sem validação de autorização no cliente
            // Qualquer valor é aceito sem verificar saldo ou permissões
            val prefs = getSharedPreferences("vulnbank_prefs", MODE_PRIVATE)
            val token = prefs.getString("auth_token", "")

            // M5 - Comunicação em HTTP sem TLS
            // Token e dados da transferência trafegam em texto claro
            Thread {
                try {
                    val url = URL("http://10.0.2.2:8080/transfer")
                    val connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "POST"
                    connection.doOutput = true

                    // M3 - Token enviado sem validação de expiração
                    // M5 - Header Authorization em HTTP puro
                    connection.setRequestProperty("Authorization", token)
                    connection.setRequestProperty("Content-Type", "application/json")

                    val body = """
                        {
                            "destination": "$destination",
                            "amount": "$amount",
                            "token": "$token"
                        }
                    """.trimIndent()

                    // M6 - Logando dados financeiros sensíveis
                    Log.d("VulnBank", "Transferência: destino=$destination valor=$amount token=$token")

                    connection.outputStream.write(body.toByteArray())
                    val responseCode = connection.responseCode

                    runOnUiThread {
                        tvResult.text = "Transferência enviada! Código: $responseCode"
                        Toast.makeText(this, "Transferência realizada", Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    runOnUiThread {
                        // M6 - Expondo detalhes internos de erro na UI
                        tvResult.text = "Erro: ${e.message}"
                    }
                }
            }.start()
        }
    }
}
