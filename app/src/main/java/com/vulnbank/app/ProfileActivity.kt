package com.vulnbank.app

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    // M8 - Informações sensíveis de infraestrutura hardcoded
    private val API_KEY          = "sk-vulnbank-prod-1234567890abcdef"
    private val INTERNAL_API_URL = "http://internal.vulnbank.com/api/v1"
    private val DB_CONNECTION    = "jdbc:mysql://192.168.1.100:3306/vulnbank_prod"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val prefs    = getSharedPreferences("vulnbank_prefs", MODE_PRIVATE)
        val username = prefs.getString("username", "")

        // M8 - Exibindo informações internas de configuração na UI
        findViewById<TextView>(R.id.tvProfile).text = """
            Usuário: $username
            API Key: $API_KEY
            Servidor: $INTERNAL_API_URL
        """.trimIndent()

        // M6 - Logando configurações sensíveis de infraestrutura
        Log.d("VulnBank", "Profile - API_KEY: $API_KEY DB: $DB_CONNECTION")
    }
}
